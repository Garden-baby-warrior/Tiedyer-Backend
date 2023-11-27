package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Badge;
import com.cnzakii.tiedyer.entity.UserBadge;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.mapper.UserBadgeMapper;
import com.cnzakii.tiedyer.model.dto.badge.BadgeDTO;
import com.cnzakii.tiedyer.model.dto.badge.BadgeResult;
import com.cnzakii.tiedyer.service.BadgeService;
import com.cnzakii.tiedyer.service.UserBadgeService;
import com.cnzakii.tiedyer.service.UserService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户徽章表 - 记录用户兑换的徽章 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-25
 */
@Service
public class UserBadgeServiceImpl extends ServiceImpl<UserBadgeMapper, UserBadge> implements UserBadgeService {

    @Resource
    private UserBadgeMapper userBadgeMapper;

    @Resource
    private BadgeService badgeService;

    @Resource
    private UserService userService;

    /**
     * 兑换徽章
     *
     * @param userId  用户ID
     * @param badgeId 徽章ID
     */
    @Override
    @Transactional
    public void redeemBadge(Long userId, Long badgeId) {
        // 获取徽章对象
        Badge badge = badgeService.getById(badgeId);
        if (badge == null) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "徽章不存在");
        }

        // 根据userId 查找用户已经获取到的徽章列表
        List<UserBadge> redeemedBadges = userBadgeMapper.selectList(new LambdaQueryWrapper<UserBadge>().eq(UserBadge::getUserId, userId));

        // 判断是否被兑换过
        redeemedBadges.stream()
                .filter(userBadge -> userBadge.getBadgeId().equals(badgeId))
                .findAny()
                .ifPresent(b -> {
                    throw new BusinessException(ResponseStatus.FAIL, "徽章已经被兑换");
                });

        // 先尝试扣除积分
        userService.decreasePoints(userId, badge.getPoints(), "兑换徽章：" + badge.getName());

        // 再兑换徽章
        UserBadge userBadge = new UserBadge();
        userBadge.setUserId(userId);
        userBadge.setBadgeId(badgeId);
        int i = userBadgeMapper.insert(userBadge);

        if (i == 0) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "兑换徽章失败");
        }

    }

    /**
     * 根据userId获取徽章获取情况
     *
     * @param userId 用户ID
     * @return 徽章获取情况
     */
    @Override
    public BadgeResult getBadgeResult(Long userId) {
        // 根据userId 查找用户已经获取到的徽章列表
        List<UserBadge> redeemedBadges = userBadgeMapper.selectList(new LambdaQueryWrapper<UserBadge>().eq(UserBadge::getUserId, userId));

        // 查询所有的徽章列表
        List<Badge> list = badgeService.list();

        if (CollectionUtils.isEmpty(list)) {
            return new BadgeResult();
        }

        List<BadgeDTO> result = new ArrayList<>();
        for (Badge badge : list) {
            // 先将badge转化成badgeDTO
            BadgeDTO badgeDTO = MyBeanUtils.copyProperties(badge, BadgeDTO.class);
            badgeDTO.setBadgeId(badge.getId());
            // 判断该徽章是否被用户所获取
            if (CollectionUtils.isEmpty(redeemedBadges)) {
                badgeDTO.setRedeemed(false);
            } else {
                for (UserBadge userBadge : redeemedBadges) {
                    if (Objects.equals(badge.getId(), userBadge.getBadgeId())) {
                        badgeDTO.setRedeemed(true);
                        badgeDTO.setRedeemTime(userBadge.getRedeemTime());
                        break;
                    }
                }
            }

            // 写入List集合
            result.add(badgeDTO);
        }

        // 排序 result 列表
        result.sort((badge1, badge2) -> {
            if (badge1.isRedeemed() != badge2.isRedeemed()) {
                return badge1.isRedeemed() ? -1 : 1; // 如果 Collected 不同，将 Collected 为 true 的排在前面
            } else if (badge1.isRedeemed()) {
                // 如果 Collected 都为 true，按 CollectTime 降序排序
                return badge2.getRedeemTime().compareTo(badge1.getRedeemTime());
            } else {
                // 如果 Collected 都为 false，按 BadgeId 升序排序
                return badge1.getBadgeId().compareTo(badge2.getBadgeId());
            }
        });


        return new BadgeResult(result, (redeemedBadges == null) ? 0 : redeemedBadges.size());
    }
}
