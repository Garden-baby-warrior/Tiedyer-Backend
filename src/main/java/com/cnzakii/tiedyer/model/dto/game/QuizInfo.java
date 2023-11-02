package com.cnzakii.tiedyer.model.dto.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * QuizInfo 类表示有关每日问题和剩余问题数量的信息。
 * 它用于将问题详细信息与剩余问题数的信息结合起来。
 *
 * @author Zaki
 * @since 2023-11-03
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"question.answer"})
public class QuizInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -2335952350542284641L;

    // 包含的问题信息
    private QuestionDTO question;

    // 剩余的题目数量
    private int remainingQuestions;
}
