package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.UserBadge;
import com.cnzakii.tiedyer.model.dto.badge.BadgeResult;

/**
 * <p>
 * 用户徽章表 - 记录用户兑换的徽章 服务类
 * </p>
 *
 * @author zaki
 * @since 2023-11-25
 */
public interface UserBadgeService extends IService<UserBadge> {


    /**
     * 兑换徽章
     *
     * @param userId  用户ID
     * @param badgeId 徽章ID
     */
    void redeemBadge(Long userId, Long badgeId);

    /**
     * 根据userId获取徽章获取情况
     *
     * @param userId 用户ID
     * @return 徽章获取情况
     */
    BadgeResult getBadgeResult(Long userId);


}
