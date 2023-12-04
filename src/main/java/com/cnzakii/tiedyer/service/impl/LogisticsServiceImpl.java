package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Logistics;
import com.cnzakii.tiedyer.entity.ShippingAddress;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.mapper.LogisticsMapper;
import com.cnzakii.tiedyer.service.LogisticsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品物流表 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-12-04
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {


    @Resource
    private LogisticsMapper logisticsMapper;


    /**
     * 初始化物流信息
     *
     * @param orderId 订单id
     * @param address 用户的收货地址
     */
    @Override
    public void initLogisticsInfo(Long orderId, ShippingAddress address) {
        Logistics logistics = new Logistics();
        logistics.setOrderId(orderId);
        logistics.setRecipientName(address.getContactName());
        logistics.setRecipientPhone(address.getContactPhone());
        logistics.setRecipientArea(address.getArea());
        logistics.setRecipientAddress(address.getAddress());
        logistics.setStatus(-1);

        logisticsMapper.insert(logistics);
    }

    /**
     * 根据订单id返回物流信息
     *
     * @param orderId 订单id
     * @return 物流信息
     */
    @Override
    public Logistics getLogisticsByOrderId(Long orderId) {
        return logisticsMapper.selectOne(new LambdaQueryWrapper<Logistics>().eq(Logistics::getOrderId, orderId));
    }


    /**
     * 根据订单id改变物流信息状态
     *
     * @param status  状态 -1 未生效，0 未发货， 1 已发货，2 已签收
     * @param orderIds 订单ID,支持批量操作
     */
    @Override
    public void updateLogisticsStatue(Integer status, Long... orderIds) {
        if (status != 0 && status != 1 && status != 2) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "更新的状态无效");
        }

        int i = logisticsMapper.update(null, new LambdaUpdateWrapper<Logistics>()
                .set(Logistics::getStatus, status)
                .in(Logistics::getOrderId, (Object[]) orderIds));

        if (i == 0) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "更新失败");
        }

    }


    /**
     * 根据订单id 删除物流信息
     *
     * @param orderId 订单id
     */
    @Override
    public void deleteLogisticsInfo(Long orderId) {
        // 物流信息处于未生效状态才能被删除
        logisticsMapper.delete(new LambdaQueryWrapper<Logistics>()
                .eq(Logistics::getOrderId, orderId)
                .eq(Logistics::getStatus, -1));

    }


}
