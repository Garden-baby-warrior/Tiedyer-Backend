package com.cnzakii.tiedyer.model.request.order;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单支付请求
 *
 * @author Zaki
 * @since 2023-12-03
 **/
@Data
public class PayOrderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8428945206369968374L;

    private Long[] orderIds;
}
