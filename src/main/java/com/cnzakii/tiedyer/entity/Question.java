package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 答题游戏-题库
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_question_bank")
public class Question implements Serializable {

    @Serial
    private static final long serialVersionUID = 3759424903451354355L;

    /**
     * 题目id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目
     */
    private String title;

    /**
     * 图片路径
     */
    private String image;

    /**
     * 选项（逗号分割）
     */
    private String option;

    /**
     * 正确答案
     */
    private Integer answer;

    /**
     * 解析
     */
    private String analysis;
}
