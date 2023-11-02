package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.model.dto.game.QuizInfo;
import com.cnzakii.tiedyer.service.QuestionGameService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 答题游戏 接口
 *
 * @author zaki
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/question")
public class QuestionGameController {


    @Resource
    private QuestionGameService questionGameService;


    /**
     * 获取每日题目
     *
     * @return 题目
     */
    @GetMapping("/daily")
    public ResponseResult<QuizInfo> getDailyQuestion() {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        // 查看用户今日是否答过题
        boolean b = questionGameService.isParticipate(userId);
        if (b) {
            return ResponseResult.fail("今日已完成答题！");
        }

        // 获取用户每日题目
        QuizInfo q = questionGameService.getDailyQuestionByUserId(userId);

        return ResponseResult.success(q);
    }

}
