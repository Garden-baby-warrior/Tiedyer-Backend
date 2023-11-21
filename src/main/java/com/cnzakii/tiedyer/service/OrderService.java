package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Order;
import com.cnzakii.tiedyer.model.dto.PageBean;
import com.cnzakii.tiedyer.model.dto.order.OrderDTO;

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
     * @param userId 用户id
     * @param skuId  商品id
     * @param num    商品数量
     * @return 订单id
     */
    Long createOrder(Long userId, Long skuId, Integer num);

    /**
     * 更新订单状态
     *
     * @param orderId    订单id
     * @param statusCode 状态码 -- 0取消，1未付款、2已付款、3已发货、4已签收
     */
    void updateStatus(Long orderId, Integer statusCode);

    /**
     * 获取用户订单列表
     *
     * @param userId     用户Id
     * @param timestamp  限制时间戳
     * @param statusCode 订单状态，允许多个
     * @return 分页查询结果
     */
    PageBean<OrderDTO> getOrderList(Long userId, Long timestamp, Integer... statusCode);


}
