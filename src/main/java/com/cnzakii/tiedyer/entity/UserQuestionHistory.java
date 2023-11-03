package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户答题记录表
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_user_question_history")
public class UserQuestionHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 6315479308855566966L;

    /**
     * 历史记录Id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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

    /**
     * 记录时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
