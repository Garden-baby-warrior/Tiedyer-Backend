package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * Oder 实体类
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_order")
@ToString
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = -3932833861749318020L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long skuId;

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
    private Integer paymentType;

    /**
     * 状态：0取消，1未付款、2已付款、3已发货、4已签收
     */
    private Integer status;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 逻辑删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
