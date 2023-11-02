package com.cnzakii.tiedyer.model.dto.game;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 每日问题数据传输对象
 *
 * @author Zaki
 * @since 2023-11-02
 **/
@Data
public class QuestionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1533071323429647976L;

    /**
     * 题目id
     */
    private String id;

    /**
     * 问题
     */
    private String title;

    /**
     * 选项（逗号分割）
     */
    private Map<String, String> options;

    /**
     * 正确答案，不传给前端
     */
    @JsonIgnore
    private String answer;

}
