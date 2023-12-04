package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品物流表
 * </p>
 *
 * @author zaki
 * @since 2023-12-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_logistics")
public class Logistics implements Serializable {

    @Serial
    private static final long serialVersionUID = -2206045537922592692L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 订单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 物流公司
     */
    private String logisticsCompanies;

    /**
     * 物流编号
     */
    private String logisticsCode;

    /**
     * 收件人姓名
     */
    private String recipientName;

    /**
     * 收件人电话
     */
    private String recipientPhone;

    /**
     * 收件人所处地区
     */
    private String recipientArea;

    /**
     * 收件人具体地址
     */
    private String recipientAddress;

    /**
     * 寄件人姓名
     */
    private String senderName;

    /**
     * 寄件人电话
     */
    private String senderPhone;

    /**
     * 寄件人所处地区
     */
    private String senderArea;

    /**
     * 寄件人具体地址
     */
    private String senderAddress;

    /**
     * 当前物流状态：-1 未生效，0 未发货， 1 已发货，2 已签收
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
