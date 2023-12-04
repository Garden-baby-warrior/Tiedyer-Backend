package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Logistics;
import com.cnzakii.tiedyer.entity.Order;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.service.LogisticsService;
import com.cnzakii.tiedyer.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 物流接口
 *
 * @author zaki
 * @since 2023-12-04
 */
@RestController
@RequestMapping("/logistics")
public class LogisticsController {

    @Resource
    private OrderService orderService;

    @Resource
    private LogisticsService logisticsService;

    /**
     * 根据orderId获取物流信息
     *
     * @param orderId 订单id
     * @return 物流信息
     */
    @GetMapping("/info/{orderId}")
    public ResponseResult<Logistics> getLogisticsByOrderId(@PathVariable("orderId") Long orderId) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        Order order = orderService.getById(orderId);
        if (order == null || !Objects.equals(order.getUserId(), userId)) {
            throw new BusinessException(ResponseStatus.FAIL, "该订单不属于该用户");
        }

        Logistics logistics = logisticsService.getLogisticsByOrderId(orderId);

        if (logistics == null || logistics.getStatus() == -1) {
            throw new BusinessException(ResponseStatus.FAIL, "物流信息不存在");
        }

        return ResponseResult.success(logistics);
    }

}
