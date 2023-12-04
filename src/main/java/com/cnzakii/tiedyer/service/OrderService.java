package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Order;
import com.cnzakii.tiedyer.model.dto.PageBean;
import com.cnzakii.tiedyer.model.dto.order.OrderDTO;
import com.cnzakii.tiedyer.model.dto.order.OrderReceiptDTO;

import java.util.List;

/**
 * 订单接口
 *
 * @author zaki
 * @since 2023-11-09
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     *
     * @param userId    用户id
     * @param skuId     商品id
     * @param num       商品数量
     * @param addressId 收货地址id
     * @return 订单回执
     */
    OrderReceiptDTO createOrder(Long userId, Long skuId, Integer num, Long addressId);

    /**
     * 更新订单状态
     *
     * @param orderId    订单id
     * @param statusCode 状态码 -- 0取消，1未付款、2已付款、3已发货、4已签收
     */
    void updateStatus(Long orderId, Integer statusCode);


    /**
     * 更新用户支付方式
     *
     * @param orders      订单id集合
     * @param paymentType 支付方式 1微信、2支付宝、3银行卡
     */
    void updateOrderPayStatus(Long[] orders, Integer paymentType);


    /**
     * 根据oderId集合获取对应的order信息集合
     *
     * @param orders oderId集合
     * @return order信息集合
     */
    List<Order> getOrderInfoList(Long[] orders);

    /**
     * 获取用户订单分页查询结果
     *
     * @param userId     用户Id
     * @param timestamp  限制时间戳
     * @param statusCode 订单状态，允许多个
     * @return 分页查询结果
     */
    PageBean<OrderDTO> getOrderPageResult(Long userId, Long timestamp, Integer... statusCode);


}
