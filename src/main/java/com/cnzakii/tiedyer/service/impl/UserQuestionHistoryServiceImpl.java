package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.UserQuestionHistory;
import com.cnzakii.tiedyer.mapper.UserQuestionHistoryMapper;
import com.cnzakii.tiedyer.service.UserQuestionHistoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户答题记录表 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Service
public class UserQuestionHistoryServiceImpl extends ServiceImpl<UserQuestionHistoryMapper, UserQuestionHistory> implements UserQuestionHistoryService {

    @Resource
    private UserQuestionHistoryMapper historyMapper;

    /**
     * 根据用户ID获取用户答过的问题Id列表
     *
     * @param userId 用户ID
     * @return 问题Id列表
     */
    @Override
    public List<Long> getQuestionIdListByUserInd(Long userId) {
        List<UserQuestionHistory> historyList = historyMapper.selectList(new LambdaQueryWrapper<UserQuestionHistory>().eq(UserQuestionHistory::getUserId, userId));

        // 如果为空，则直接返回空列表
        if (CollectionUtils.isEmpty(historyList)) {
            return new ArrayList<>();
        }

        // 提取列表中题目对应的Id
        return historyList.stream()
                .map(UserQuestionHistory::getQuestionId)
                .toList();
    }
}
