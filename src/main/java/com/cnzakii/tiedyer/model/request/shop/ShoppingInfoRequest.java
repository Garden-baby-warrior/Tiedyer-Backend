package com.cnzakii.tiedyer.model.request.shop;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 购物信息添加请求，用于将商品加入购物车以及添加新订单请求
 *
 * @author Zaki
 * @since 2023-11-09
 **/
@Data
public class ShoppingInfoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8756227730899732933L;

    /**
     * 商品id
     */
    @NotNull(message = "商品ID不能为null")
    private Long skuId;

    /**
     * 购买的商品数量
     */
    @NotNull
    @Min(value = 1, message = "num无效")
    private Integer num;

}
