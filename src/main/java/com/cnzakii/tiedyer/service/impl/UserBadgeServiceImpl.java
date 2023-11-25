package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.Badge;
import com.cnzakii.tiedyer.entity.UserBadge;
import com.cnzakii.tiedyer.mapper.UserBadgeMapper;
import com.cnzakii.tiedyer.model.dto.badge.BadgeDTO;
import com.cnzakii.tiedyer.model.dto.badge.BadgeResult;
import com.cnzakii.tiedyer.service.BadgeService;
import com.cnzakii.tiedyer.service.UserBadgeService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
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

    /**
     * 根据userId获取徽章获取情况
     *
     * @param userId 用户ID
     * @return 徽章获取情况
     */
    @Override
    public BadgeResult getBadgeResult(Long userId) {
        // 根据userId 查找用户已经获取到的徽章列表
        List<UserBadge> collectedBadges = userBadgeMapper.selectList(new LambdaQueryWrapper<UserBadge>().eq(UserBadge::getUserId, userId));


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
            if (CollectionUtils.isEmpty(collectedBadges)) {
                badgeDTO.setCollected(false);
            } else {
                for (UserBadge userBadge : collectedBadges) {
                    if (Objects.equals(badge.getId(), userBadge.getBadgeId())) {
                        badgeDTO.setCollected(true);
                        badgeDTO.setCollectTime(userBadge.getCollectTime());
                        break;
                    }
                }
            }

            // 写入List集合
            result.add(badgeDTO);
        }

        // 排序 result 列表
        result.sort((badge1, badge2) -> {
            if (badge1.isCollected() != badge2.isCollected()) {
                return badge1.isCollected() ? -1 : 1; // 如果 Collected 不同，将 Collected 为 true 的排在前面
            } else if (badge1.isCollected()) {
                // 如果 Collected 都为 true，按 CollectTime 降序排序
                return badge2.getCollectTime().compareTo(badge1.getCollectTime());
            } else {
                // 如果 Collected 都为 false，按 BadgeId 升序排序
                return badge1.getBadgeId().compareTo(badge2.getBadgeId());
            }
        });


        return new BadgeResult(result, (collectedBadges == null) ? 0 : collectedBadges.size());
    }
}
