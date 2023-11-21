package com.cnzakii.tiedyer.model.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单数据传输对象
 *
 * @author Zaki
 * @since 2023-11-21
 **/
@Data
public class OrderDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8357936247294651519L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;


    /**
     * 商品ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skuId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品图片
     */
    private String image;


    /**
     * 商品规格
     */
    private String spec;

    /**
     * 购买商品的实际价格
     */
    private BigDecimal actualPrice;

    /**
     * 购买的商品数量
     */
    private Integer num;

    /**
     * 订单总价
     */
    private BigDecimal amount;

    /**
     * 支付方式：1微信、2支付宝、3银行卡
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer paymentType;

    /**
     * 状态：0取消，1未付款、2已付款、3已发货、4已签收
     */
    private Integer status;
}
