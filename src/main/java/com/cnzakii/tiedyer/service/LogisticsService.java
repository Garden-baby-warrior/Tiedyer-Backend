package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Logistics;
import com.cnzakii.tiedyer.entity.ShippingAddress;

/**
 * <p>
 * 商品物流表 服务类
 * </p>
 *
 * @author zaki
 * @since 2023-12-04
 */
public interface LogisticsService extends IService<Logistics> {

    /**
     * 初始化物流信息
     *
     * @param orderId 订单id
     * @param address 用户的收货地址
     */
    void initLogisticsInfo(Long orderId, ShippingAddress address);


    /**
     * 根据订单id返回物流信息
     *
     * @param orderId 订单id
     * @return 物流信息
     */
    Logistics getLogisticsByOrderId(Long orderId);

    /**
     * 根据订单id改变物流信息状态
     *
     * @param status  状态 -1 未生效，0 未发货， 1 已发货，2 已签收
     * @param orderIds 订单ID,支持批量操作
     */
    void updateLogisticsStatue(Integer status, Long... orderIds);

    /**
     * 根据订单id 删除物流信息
     *
     * @param orderId 订单id
     */
    void deleteLogisticsInfo(Long orderId);


}
