package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户收货地址实体类
 *
 * @author zaki
 * @since 2023-11-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_shipping_address")
public class ShippingAddress implements Serializable {

    @Serial
    private static final long serialVersionUID = 6078318122535782367L;

    /**
     * 收货地址id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;


    /**
     * 地区
     */
    private String area;

    /**
     * 具体地址
     */
    private String address;

    /**
     * 优先级
     */
    private Integer priority;
}
