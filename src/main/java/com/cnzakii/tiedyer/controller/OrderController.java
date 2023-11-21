package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.entity.Order;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.model.dto.PageBean;
import com.cnzakii.tiedyer.model.dto.order.OrderDTO;
import com.cnzakii.tiedyer.model.request.order.CreateOrderRequest;
import com.cnzakii.tiedyer.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.cnzakii.tiedyer.common.http.ResponseStatus.REQUEST_ERROR;

/**
 * 订单接口
 *
 * @author zaki
 * @since 2023-11-09
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;


    /**
     * 创建订单
     *
     * @param request 创建订单请求
     * @return 订单编号
     */
    @PostMapping("/create")
    public ResponseResult<String> createOrder(@Validated @RequestBody CreateOrderRequest request) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        Long orderId = orderService.createOrder(userId, request.getSkuId(), request.getNum());

        return ResponseResult.success(String.valueOf(orderId));
    }


    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @return 订单编号
     */
    @PostMapping("/cancel/{orderId}")
    public ResponseResult<String> createOrder(@PathVariable("orderId") Long orderId) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        Order o = orderService.getById(orderId);
        if (o == null) {
            return ResponseResult.base(REQUEST_ERROR, "订单不存在");
        }
        if (!Objects.equals(o.getUserId(), userId) || !Objects.equals(o.getStatus(), 1)) {
            return ResponseResult.base(REQUEST_ERROR, REQUEST_ERROR.getDescription());
        }

        orderService.updateStatus(orderId, 0);

        return ResponseResult.success();
    }


    /**
     * 获取用户订单列表
     *
     * @param status    unpaid = 1未付款   unfinished = 2已付款、3已发货
     * @param timestamp 限制时间戳
     * @return 未付款订单列表和新的限制时间戳
     */
    @GetMapping("/list/{status}")
    public ResponseResult<PageBean<OrderDTO>> getUnpaidOrderList(@PathVariable("status") String status, @RequestParam(value = "timestamp", defaultValue = "-1") Long timestamp) {

        Integer[] statusCode = switch (status) {
            case "unpaid" -> new Integer[]{1};
            case "unfinished" -> new Integer[]{2, 3};
            default -> throw new BusinessException(REQUEST_ERROR);
        };

        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        if (timestamp == -1) {
            timestamp = System.currentTimeMillis();
        }

        PageBean<OrderDTO> result = orderService.getOrderList(userId, timestamp, statusCode);

        return ResponseResult.success(result);
    }


}
