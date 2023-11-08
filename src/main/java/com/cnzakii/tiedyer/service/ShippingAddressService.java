package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.ShippingAddress;
import com.cnzakii.tiedyer.model.dto.shop.ShippingAddressDTO;

import java.util.List;

/**
 * 用户收货地址管理接口
 *
 * @author zaki
 * @since 2023-11-01
 */
public interface ShippingAddressService extends IService<ShippingAddress> {


    /**
     * 保存用户收货地址
     *
     * @param userId       用户ID
     * @param contactName  联系人
     * @param contactPhone 联系电话
     * @param area         地区
     * @param address      地址
     * @param isDefault    是否设置为默认地址
     * @return 如果保存成功则返回ShippingAddress，保存失败返回null
     */
    ShippingAddress saveShippingAddress(Long userId, String contactName, String contactPhone, String area, String address, boolean isDefault);

    /**
     * 更新用户收货地址
     *
     * @param userId       用户ID
     * @param addressId    收货地址ID
     * @param contactName  联系人
     * @param contactPhone 联系电话
     * @param area         地区
     * @param address      地址
     * @param isDefault    是否设置为默认地址
     */
    void updateShippingAddress(Long userId, Long addressId, String contactName, String contactPhone, String area, String address, boolean isDefault);

    /**
     * 根据用户id获取用户购物地址列表
     *
     * @param userId 用户id
     * @return 用户购物地址列表
     */
    List<ShippingAddress> getShippingAddressListByUserId(Long userId);


    /**
     * 将ShippingAddress转化成ShippingAddressDTO
     *
     * @param shippingAddress 收货地址
     * @return ShippingAddressDTO
     */
    ShippingAddressDTO convertShippingAddressToDTO(ShippingAddress shippingAddress);


    /**
     * 根据userId和addressId 删除收货地址信息
     *
     * @param userId     用户ID
     * @param addressId 收货地址id
     */
    void removeByUserIdAndAddressId(Long userId, Long addressId);
}
