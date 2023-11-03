package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Question;
import com.cnzakii.tiedyer.model.dto.game.AnswerDTO;
import com.cnzakii.tiedyer.model.dto.game.QuestionDTO;

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
     * 验证用户答案,并获取正确答案和解析
     *
     * @param userId     用户ID
     * @param questionId 问题ID
     * @param answer     用户答案
     * @return 正确答案和解析
     */
    AnswerDTO verifyUerAnswer(Long userId, Long questionId,String answer);


    /**
     * 根据用户id和问题id获取正确答案和解析
     *
     * @param userId     用户ID
     * @param questionId 问题ID
     * @return 正确答案和解析
     */
    AnswerDTO getAnswerAndAnalysis(Long userId, Long questionId);


    /**
     * 根据userId获取用户每日问题
     *
     * @param userId 用户id
     * @return 题目集合
     */
    QuestionDTO[] getDailyQuestionByUserId(Long userId);


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
