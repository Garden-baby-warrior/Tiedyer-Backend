package com.cnzakii.tiedyer.controller;


import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.entity.ShippingAddress;
import com.cnzakii.tiedyer.model.dto.shop.ShippingAddressDTO;
import com.cnzakii.tiedyer.model.request.address.SaveShippingAddressRequest;
import com.cnzakii.tiedyer.model.request.address.UpdateShippingAddressRequest;
import com.cnzakii.tiedyer.service.ShippingAddressService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/shipping-address")
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
    public ResponseResult<String> saveShippingAddress(@Validated @RequestBody SaveShippingAddressRequest request) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        // 将地区数组转化成字符串
        String area = Optional.ofNullable(request.getArea())
                .map(list -> String.join(",", list))
                .orElse(null);

        shippingAddressService.saveShippingAddress(
                userId,
                request.getContactName(),
                request.getContactPhone(),
                area,
                request.getAddress(),
                request.isDefaultAddress()
        );

        return ResponseResult.success();
    }

    /**
     * 更新用户收货地址
     *
     * @param request 更新用户收货地址请求体
     * @return ResponseResult
     */
    @PutMapping("")
    public ResponseResult<String> updateShippingAddress(@Validated @RequestBody UpdateShippingAddressRequest request) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        // 将地区数组转化成字符串
        String area = Optional.ofNullable(request.getArea())
                .map(list -> String.join(",", list))
                .orElse(null);

        shippingAddressService.updateShippingAddress(
                userId,
                request.getAddressId(),
                request.getContactName(),
                request.getContactPhone(),
                area,
                request.getAddress(),
                request.isDefaultAddress()
        );

        return ResponseResult.success();
    }

    /**
     * 删除用户收货地址
     *
     * @param addressId 地址Id
     * @return success
     */
    @DeleteMapping("/{addressId}")
    public ResponseResult<String> deleteShippingAddress(@PathVariable("addressId") Long addressId) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        shippingAddressService.removeByUserIdAndAddressId(userId, addressId);
        return ResponseResult.success();
    }

    /**
     * 获取用户收货地址列表
     *
     * @return 用户收货地址列表
     */
    @GetMapping("/list")
    public ResponseResult<List<ShippingAddressDTO>> getShippingAddressList() {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

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
