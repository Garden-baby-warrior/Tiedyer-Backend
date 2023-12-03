package com.cnzakii.tiedyer.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单回执，包括订单编号数组和总共需要支付的金额
 *
 * @author Zaki
 * @since 2023-12-03
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReceiptDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3752760911315412630L;

    /**
     * 订单编号
     */
    private String[] orderId;

    /**
     * 总金额
     */
    private BigDecimal amount;
}
