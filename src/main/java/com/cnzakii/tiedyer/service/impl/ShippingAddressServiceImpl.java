package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.ShippingAddress;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.mapper.ShippingAddressMapper;
import com.cnzakii.tiedyer.model.dto.shop.ShippingAddressDTO;
import com.cnzakii.tiedyer.service.ShippingAddressService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 用户购物地址管理实现类
 *
 * @author zaki
 * @since 2023-11-01
 */
@Service
public class ShippingAddressServiceImpl extends ServiceImpl<ShippingAddressMapper, ShippingAddress> implements ShippingAddressService {

    @Resource
    private ShippingAddressMapper shippingAddressMapper;


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
    @Transactional
    @Override
    public ShippingAddress saveShippingAddress(Long userId, String contactName, String contactPhone, String area, String address, boolean isDefault) {
        // 保存上限
        int MAX_SHIPPING_ADDRESS_NUM = 5;

        // 先获取用户购物地址列表
        List<ShippingAddress> list = getShippingAddressListByUserId(userId);

        if (!CollectionUtils.isEmpty(list) && list.size() >= MAX_SHIPPING_ADDRESS_NUM) {
            throw new BusinessException(ResponseStatus.FAIL, "用户保存的收货地址数量已达上限");
        }

        ShippingAddress shippingAddress = new ShippingAddress();

        shippingAddress.setUserId(userId);
        shippingAddress.setContactName(contactName);
        shippingAddress.setContactPhone(contactPhone);
        shippingAddress.setArea(area);
        shippingAddress.setAddress(address);

        if (CollectionUtils.isEmpty(list) || isDefault) {
            // 如果是首次添加，默认为自动==》优先级为1
            shippingAddress.setPriority(1);
        } else {
            // 优先级设置成最后
            shippingAddress.setPriority(list.size() + 1);
        }

        // 先更新优先级
        if (!CollectionUtils.isEmpty(list) && isDefault) {
            shippingAddressMapper.incrementPrioritiesInRange(userId, 1, list.size());
        }

        // 再插入新数据
        int insert = shippingAddressMapper.insert(shippingAddress);
        if (insert != 1) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "保存收货地址失败");
        }


        return shippingAddress;
    }

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
    @Transactional
    @Override
    public void updateShippingAddress(Long userId, Long addressId, String contactName, String contactPhone, String area, String address, boolean isDefault) {
        // 先获取用户购物地址列表
        List<ShippingAddress> list = getShippingAddressListByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "需要更新的收货地址不存在");
        }

        // 根据userId和addressId获取元素
        ShippingAddress before = list.stream()
                .filter(s -> Objects.equals(s.getUserId(), userId) && Objects.equals(s.getId(), addressId))
                .findFirst()
                .orElse(null);

        if (before == null) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "需要更新的收货地址不存在");
        }

        // 获取旧优先级
        Integer oldPriority = before.getPriority();
        if (oldPriority == 1 && !isDefault) {
            // 不允许将默认地址设置成非默认地址
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "不允许将默认地址设置成非默认地址");
        }

        // 设置新优先级
        Integer newPriority;
        if (isDefault) {
            newPriority = 1;
        } else {
            newPriority = oldPriority;
        }

        // 先更新优先级
        if (list.size() > 1 && !Objects.equals(oldPriority, newPriority)) {
            shippingAddressMapper.incrementPrioritiesInRange(userId, 1, oldPriority);
        }

        // 再更新数据
        int update = shippingAddressMapper.update(null, new LambdaUpdateWrapper<ShippingAddress>()
                .eq(ShippingAddress::getId, addressId)
                .set(ShippingAddress::getContactName, contactName)
                .set(ShippingAddress::getContactPhone, contactPhone)
                .set(ShippingAddress::getArea, area)
                .set(ShippingAddress::getAddress, address)
                .set(ShippingAddress::getPriority, newPriority));

        if (update != 1) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "保存收货地址失败");
        }

    }

    /**
     * 根据用户id获取用户购物地址列表
     *
     * @param userId 用户id
     * @return 用户购物地址列表
     */
    @Override
    public List<ShippingAddress> getShippingAddressListByUserId(Long userId) {
        // 根据用户id获取用户购物地址列表
        return shippingAddressMapper.selectList(new LambdaQueryWrapper<ShippingAddress>()
                .eq(ShippingAddress::getUserId, userId)
                .orderByAsc(ShippingAddress::getPriority)
        );
    }


    /**
     * 将ShippingAddress转化成ShippingAddressDTO
     *
     * @param shippingAddress 收货地址
     * @return ShippingAddressDTO
     */
    @Override
    public ShippingAddressDTO convertShippingAddressToDTO(ShippingAddress shippingAddress) {

        ShippingAddressDTO shippingAddressDTO = MyBeanUtils.copyProperties(shippingAddress, ShippingAddressDTO.class);
        shippingAddressDTO.setAddressId(shippingAddress.getId());

        // 获取地址数组
        String[] area = shippingAddress.getArea().split(",");
        shippingAddressDTO.setArea(area);

        return shippingAddressDTO;
    }
}
