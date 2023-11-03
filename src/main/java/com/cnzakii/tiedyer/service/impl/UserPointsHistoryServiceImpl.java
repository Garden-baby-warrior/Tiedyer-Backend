package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.UserPointsHistory;
import com.cnzakii.tiedyer.mapper.UserPointsHistoryMapper;
import com.cnzakii.tiedyer.service.UserPointsHistoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户积分获取/消耗记录表 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Service
public class UserPointsHistoryServiceImpl extends ServiceImpl<UserPointsHistoryMapper, UserPointsHistory> implements UserPointsHistoryService {

    @Resource
    private UserPointsHistoryMapper historyMapper;


    /**
     * 为用户添加积分
     *
     * @param userId      用户ID
     * @param points      添加的积分数
     * @param description 积分描述
     */
    @Override
    public void addPointLog(Long userId, int points, String description) {
        UserPointsHistory userPointsHistory = new UserPointsHistory();
        userPointsHistory.setUserId(userId);
        userPointsHistory.setPoints(points);
        userPointsHistory.setDescription(description);

        historyMapper.insert(userPointsHistory);
    }
}
