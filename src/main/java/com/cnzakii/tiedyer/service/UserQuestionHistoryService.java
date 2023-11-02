package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.UserQuestionHistory;

import java.util.List;

/**
 * <p>
 * 用户答题记录表 服务类
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
public interface UserQuestionHistoryService extends IService<UserQuestionHistory> {

    /**
     * 根据用户ID获取用户答过的问题Id列表
     *
     * @param userId 用户ID
     * @return 问题Id列表
     */
    List<Long> getQuestionIdListByUserInd(Long userId);
}
