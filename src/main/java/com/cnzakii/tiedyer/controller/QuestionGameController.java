package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.model.dto.game.AnswerDTO;
import com.cnzakii.tiedyer.model.dto.game.QuestionDTO;
import com.cnzakii.tiedyer.model.request.game.QuestionAnswerRequest;
import com.cnzakii.tiedyer.service.QuestionGameService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult<QuestionDTO[]> getDailyQuestion() {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        // 查看用户今日是否答过题
        boolean b = questionGameService.isParticipate(userId);
        if (b) {
            return ResponseResult.fail("今日已完成答题！");
        }

        // 获取用户每日题目
        QuestionDTO[] q = questionGameService.getDailyQuestionByUserId(userId);

        return ResponseResult.success(q);
    }


    /**
     * 用户答案验证
     *
     * @param request 用户答案验证请求
     * @return 正确答案以及解析
     */
    @PostMapping("/daily/verify")
    public ResponseResult<AnswerDTO> verifyUerAnswer(@Validated @RequestBody QuestionAnswerRequest request) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        // 验证用户答案,并获取正确答案和解析
        AnswerDTO answerDTO =  questionGameService.verifyUerAnswer(userId, request.getQuestionId(), request.getAnswer());


        return ResponseResult.success(answerDTO);
    }

}
