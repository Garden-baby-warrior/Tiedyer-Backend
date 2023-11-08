package com.cnzakii.tiedyer.model.dto.shop;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户收货地址数据传输对象
 *
 * @author Zaki
 * @since 2023-11-01
 **/
@Data
public class ShippingAddressDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7166581222452760934L;

    // 收货地址id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long addressId;

    // 用户id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    // 联系人姓名
    private String contactName;

    // 联系电话
    private String contactPhone;

    // 地区
    private String[] area;

    // 具体地址
    private String address;

    // 优先级
    private Integer priority;
}
