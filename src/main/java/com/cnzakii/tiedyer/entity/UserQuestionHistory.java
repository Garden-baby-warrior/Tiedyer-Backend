package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 用户答题记录表
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user_question_history")
public class UserQuestionHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 6315479308855566966L;

    /**
     * 历史记录Id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 题目Id
     */
    private Long questionId;

    /**
     * 是否答题正确
     */
    private Integer isCorrect;
}
