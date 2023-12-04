package com.cnzakii.tiedyer.rabbitMQListener;

import com.cnzakii.tiedyer.entity.Order;
import com.cnzakii.tiedyer.service.LogisticsService;
import com.cnzakii.tiedyer.service.OrderService;
import com.cnzakii.tiedyer.service.SkuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.cnzakii.tiedyer.common.constant.RabbitMQConstants.DELAY_QUEUE;

/**
 * 订单超时监听器
 *
 * @author Zaki
 * @since 2023-11-21
 **/
@Slf4j
@Component
public class OrderTimeoutListener {

    @Resource
    private SkuService skuService;

    @Resource
    private OrderService orderService;

    @Resource
    private LogisticsService logisticsService;

    /**
     * 订单超时监听
     */
    @RabbitListener(queues = DELAY_QUEUE)
    @Transactional
    public void receive(Message message) {
        Long orderId = Long.valueOf(new String(message.getBody()));

        // 查询订单信息
        Order order = orderService.getById(orderId);

        // 如果订单不处于未付款状态，则不进行处理
        if (order.getStatus() != 1) {
            return;
        }

        // 先释放库存
        skuService.increaseStock(order.getSkuId(), order.getNum());

        // 再改变订单状态
        orderService.updateStatus(orderId, 0);

        // 最后删除物流信息
        logisticsService.deleteLogisticsInfo(orderId);

        log.info("当前时间：{}，订单({})因支付超时取消", LocalDateTime.now(), orderId);
    }

}
