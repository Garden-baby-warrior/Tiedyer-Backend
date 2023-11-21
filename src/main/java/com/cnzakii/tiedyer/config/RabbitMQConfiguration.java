package com.cnzakii.tiedyer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.cnzakii.tiedyer.common.constant.RabbitMQConstants.*;


/**
 * RabbitMQ配置类
 *
 * @author Zaki
 * @since 2023-11-21
 **/
@Configuration
public class RabbitMQConfiguration {

    // 声明延迟队列
    @Bean("delayQueue")
    public Queue delayQueue() {
        return new Queue(DELAY_QUEUE);
    }

    // 声明延迟交换机，延迟消息由 rabbitmq_delayed_message_exchange 插件实现
    /*
        安装插件后会生成新的 Exchange 类型 x-delayed-message，
        该类型消息支持延迟投递机制，接收到消息后并未立即将消息投递至目标队列，
        而是存储在 mnesia(一个分布式数据库) 中，
        随后监测消息延迟时间，如达到可投递时间时将其通过 x-delayed-type 类型标记的交换机投递至目标队列。
     */
    @Bean("delayExchange")
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }

    // 声明延迟队列的绑定关系
    @Bean
    public Binding delayBinding(@Qualifier("delayQueue") Queue queue,
                                @Qualifier("delayExchange") CustomExchange delayExchange) {
        return BindingBuilder.bind(queue).to(delayExchange).with(DELAY_QUEUE_ROUTING_KEY).noargs();
    }


}
