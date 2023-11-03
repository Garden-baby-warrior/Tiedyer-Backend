package com.cnzakii.tiedyer.model.dto.game;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 每日答题--答案以及解析
 *
 * @author Zaki
 * @since 2023-11-03
 **/
@Data
public class AnswerDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 324298482377147543L;

    /**
     * 正确答案，不传给前端
     */
    private String answer;

    /**
     * 解析，不传给前端
     */
    private String analysis;
}
