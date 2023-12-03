package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Order;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.model.request.order.PayOrderRequest;
import com.cnzakii.tiedyer.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 支付接口
 *
 * @author Zaki
 * @since 2023-12-03
 **/
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private OrderService orderService;

    /**
     * 订单支付接口
     *
     * @param paymentType 支付方式 1微信、2支付宝、3银行卡
     * @param request 支付请求
     * @return success
     */
    @PostMapping("/order/{paymentType}")
    public ResponseResult<String> payOrder(@PathVariable("paymentType") Integer paymentType, @RequestBody PayOrderRequest request) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        // 订单id集合
        Long[] orderIds = request.getOrderIds();
        // 验证订单是否能够被支付
        List<Order> orderList = orderService.getOrderInfoList(orderIds);
        if (orderIds.length != orderList.size()) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR);
        }
        for (Order order : orderList) {
            if (!Objects.equals(order.getUserId(), userId) || order.getStatus() != 1) {
                throw new BusinessException(ResponseStatus.REQUEST_ERROR);
            }
        }

        // 支付。。。。。


        // 更新用户支付方式
        orderService.updateOrderPayStatus(orderIds, paymentType);

        return ResponseResult.success("支付成功");
    }

}
