package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Question;
import com.cnzakii.tiedyer.model.dto.game.QuestionDTO;
import com.cnzakii.tiedyer.model.dto.game.QuizInfo;

import java.util.Map;

/**
 * <p>
 * 答题游戏-题库 服务类
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
public interface QuestionGameService extends IService<Question> {

    /**
     * 查看用户今日是否答过题
     *
     * @param userId 用户id
     * @return 是否答过题
     */
    boolean isParticipate(Long userId);


    /**
     * 根据userId获取用户每日问题
     *
     * @param userId 用户id
     * @return 题目
     */
    QuizInfo getDailyQuestionByUserId(Long userId);


    /**
     * 为用户初始化每日问题列表
     *
     * @param userId 用户ID
     * @return 每日问题map
     */
    Map<String, String> initDailyQuestionByUserId(Long userId);


    /**
     * 将Question转化成DTO
     *
     * @param question question对象
     * @return QuestionDTO
     */
    QuestionDTO convertQuestionToDTO(Question question);

}
