package com.cnzakii.tiedyer.model.dto.shop;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品信息数据传输对象
 *
 * @author Zaki
 * @since 2023-11-14
 **/
@Data
public class SkuDTO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long skuId;

    /**
     * 商品规格
     */
    private String spec;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer stock;
}
