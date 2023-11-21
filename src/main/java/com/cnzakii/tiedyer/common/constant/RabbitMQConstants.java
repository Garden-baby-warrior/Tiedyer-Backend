package com.cnzakii.tiedyer.common.constant;

/**
 * RabbitMQ 常量
 *
 * @author Zaki
 * @since 2023-11-21
 **/
public interface RabbitMQConstants {

    // 延迟交换机
    String DELAY_EXCHANGE = "delay.exchange";
    // 延迟队列
    String DELAY_QUEUE = "delay.queue";
    // 延迟队列路由Key
    String DELAY_QUEUE_ROUTING_KEY = "delay.queue.routing.key";

}
