package com.cnzakii.tiedyer.model.request.game;

import com.cnzakii.tiedyer.common.validation.annotation.FixedStrValues;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 每日答题，用户答案验证请求
 *
 * @author Zaki
 * @since 2023-11-03
 **/
@Data
public class QuestionAnswerRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8499167440393170343L;

    @NotNull(message = "问题id不能为空")
    private Long questionId;

    @FixedStrValues(values = {"A", "B", "C", "D"}, message = "选项无效")
    private String answer;
}
