package com.cnzakii.tiedyer.controller;


import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.entity.ShippingAddress;
import com.cnzakii.tiedyer.model.dto.shop.ShippingAddressDTO;
import com.cnzakii.tiedyer.model.request.shop.SaveShippingAddressRequest;
import com.cnzakii.tiedyer.service.ShippingAddressService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户收货地址接口
 *
 * @author Zaki
 * @since 2023-11-01
 **/
@RestController
@RequestMapping("/ship/address")
@Validated
public class ShippingAddressController {

    @Resource
    private ShippingAddressService shippingAddressService;


    /**
     * 保存用户收货地址
     *
     * @param request 保存用户收货地址请求体
     * @return ResponseResult
     */
    @PostMapping("/save")
    public ResponseResult<String> saveShippingAddress(@RequestBody SaveShippingAddressRequest request) {
        // 获取userId
        Long userId = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        // 将地区数组转化成字符串
        String area = Optional.ofNullable(request.getArea())
                .map(list -> String.join(",", list))
                .orElse(null);

        ShippingAddress shippingAddress = shippingAddressService.saveShippingAddress(
                userId,
                request.getContactName(),
                request.getContactPhone(),
                area,
                request.getAddress(),
                request.isDefault()
        );

        return null;
    }


    /**
     * 获取用户收货地址列表
     *
     * @return 用户收货地址列表
     */
    @GetMapping("")
    public ResponseResult<List<ShippingAddressDTO>> getShippingAddressList() {
        // 获取userId
        Long userId = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        List<ShippingAddress> list = shippingAddressService.getShippingAddressListByUserId(userId);

        List<ShippingAddressDTO> result = new ArrayList<>(list.size());
        // 将ShippingAddress转化成ShippingAddressDTO
        for (ShippingAddress s : list) {
            ShippingAddressDTO shippingAddressDTO = shippingAddressService.convertShippingAddressToDTO(s);
            result.add(shippingAddressDTO);
        }

        return ResponseResult.success(result);
    }


}
