package com.cnzakii.tiedyer.model.dto.shop;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 产品属性数据传输对象
 *
 * @author Zaki
 * @since 2023-11-14
 **/
@Data
public class SpuSpecDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8398870452400539527L;

    /**
     * 规格名
     */
    private String name;

    /**
     * 规格值
     */
    private String[] value;

}
