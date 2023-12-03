package com.cnzakii.tiedyer.model.request.order;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单集合创建请求体
 *
 * @author Zaki
 * @since 2023-11-21
 **/
@Data
public class CreateOrderListRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7412518012501128241L;

    /**
     * 商品id集合
     */
    private Long[] skuIds;
}
