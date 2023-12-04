package com.cnzakii.tiedyer.model.request.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单创建请求体
 *
 * @author Zaki
 * @since 2023-11-21
 **/
@Data
public class CreateOrderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7412518012501128241L;

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

    /**
     * 收货地址Id
     */
    @NotNull(message = "收货地址ID不能为null")
    private Long addressId;
}
