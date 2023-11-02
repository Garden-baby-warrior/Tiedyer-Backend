package com.cnzakii.tiedyer.model.request.shop;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 保存用户收货地址请求体
 *
 * @author Zaki
 * @since 2023-11-01
 **/
@Data
public class SaveShippingAddressRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5898649989304540621L;

    // 联系人姓名
    @NotEmpty(message = "联系人不能为空")
    private String contactName;

    // 联系电话
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "联系电话无效")
    private String contactPhone;

    // 地区
    @NotNull(message = "地区不能为空")
    private String[] area;

    // 具体地址
    @NotNull(message = "地址不能为空")
    private String address;

    // 是否为默认
    @NotNull(message = "isDefault值无效")
    private boolean isDefault;
}
