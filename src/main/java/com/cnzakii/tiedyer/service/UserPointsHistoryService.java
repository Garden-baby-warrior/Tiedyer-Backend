package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.UserPointsHistory;

/**
 * <p>
 * 用户积分获取/消耗记录表 服务类
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
public interface UserPointsHistoryService extends IService<UserPointsHistory> {

    /**
     * 为用户添加积分
     *
     * @param userId      用户ID
     * @param points      添加的积分数
     * @param description 积分描述
     */
    void addPointLog(Long userId, int points, String description);
}
