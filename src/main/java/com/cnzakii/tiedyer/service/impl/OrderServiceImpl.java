package com.cnzakii.tiedyer.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Order;
import com.cnzakii.tiedyer.entity.Sku;
import com.cnzakii.tiedyer.entity.Spu;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.mapper.OrderMapper;
import com.cnzakii.tiedyer.model.dto.PageBean;
import com.cnzakii.tiedyer.model.dto.order.OrderDTO;
import com.cnzakii.tiedyer.model.dto.order.OrderReceiptDTO;
import com.cnzakii.tiedyer.service.OrderService;
import com.cnzakii.tiedyer.service.SkuService;
import com.cnzakii.tiedyer.service.SpuService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.cnzakii.tiedyer.common.constant.RabbitMQConstants.DELAY_EXCHANGE;
import static com.cnzakii.tiedyer.common.constant.RabbitMQConstants.DELAY_QUEUE_ROUTING_KEY;

/**
 * 订单接口实现
 *
 * @author zaki
 * @since 2023-11-09
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private SkuService skuService;

    @Resource
    private SpuService spuService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private Snowflake snowflake;

    @Resource
    private RabbitTemplate rabbitTemplate;


    // 订单支付期限，超时取消订单
    private final Integer ODER_PAYMENT_TTL = 15 * 60 * 1000;

    /**
     * 创建订单
     *
     * @param userId 用户id
     * @param skuId  商品id
     * @param num    商品数量
     * @return 订单回执
     */
    @Override
    @Transactional
    public OrderReceiptDTO createOrder(Long userId, Long skuId, Integer num) {
        // 先查看库存是否充足
        Sku sku = skuService.getById(skuId);
        if (sku == null) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "商品不存在");
        }

        if (num > sku.getStock()) {
            throw new BusinessException(ResponseStatus.FAIL, "商品库存不足");
        }

        // 先减少库存
        skuService.decreaseStock(skuId, num);

        // 生成订单
        Long orderId = snowflake.nextId();
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setSkuId(skuId);
        order.setActualPrice(sku.getPrice());
        order.setNum(num);
        order.setAmount(sku.getPrice().multiply(new BigDecimal(num)));
        order.setStatus(1);

        orderMapper.insert(order);

        //  存入RabbitMQ，设置15分钟有效期
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE, DELAY_QUEUE_ROUTING_KEY, String.valueOf(orderId), msg -> {
            // 设置消息的延迟时间
            msg.getMessageProperties().setDelay(ODER_PAYMENT_TTL);
            return msg;
        });

        // TODO 增加产品销量


        return new OrderReceiptDTO(new String[]{String.valueOf(orderId)}, order.getAmount());
    }

    /**
     * 更新订单状态
     *
     * @param orderId    订单id
     * @param statusCode 状态码 -- 0取消，1未付款、2已付款、3已发货、4已签收
     */
    @Override
    public void updateStatus(Long orderId, Integer statusCode) {

        int i = orderMapper.update(null, new LambdaUpdateWrapper<Order>().set(Order::getStatus, statusCode).eq(Order::getId, orderId));

        if (i == 0) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "更新订单状态失败");
        }

    }


    /**
     * 更新用户支付方式
     *
     * @param orders      订单id集合
     * @param paymentType 支付方式 1微信、2支付宝、3银行卡
     */
    @Override
    public void updateOrderPayStatus(Long[] orders, Integer paymentType) {
        if (paymentType != 1 && paymentType != 2 && paymentType != 3) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "支付方式错误");
        }
        int i = orderMapper.update(null, new LambdaUpdateWrapper<Order>()
                .set(Order::getStatus, 2)
                .set(Order::getPaymentType, paymentType)
                .in(Order::getId, (Object[]) orders));

        if (i != orders.length) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "支付订单失败");
        }

    }

    /**
     * 根据oderId集合获取对应的order信息集合
     *
     * @param orders oderId集合
     * @return order信息集合
     */
    @Override
    public List<Order> getOrderInfoList(Long[] orders) {
        return orderMapper.selectList(new LambdaQueryWrapper<Order>().in(Order::getId, (Object[]) orders));
    }


    /**
     * 获取用户订单查询结果
     *
     * @param userId    用户Id
     * @param timestamp 限制时间戳
     * @return 分页查询结果
     */
    @Override
    public PageBean<OrderDTO> getOrderPageResult(Long userId, Long timestamp, Integer... statusCode) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        List<Order> orderList = orderMapper.selectOrderList(userId, dateTime, 5, statusCode);

        if (CollectionUtils.isEmpty(orderList)) {
            // 如果为空，则直接返回空对象
            return new PageBean<>(new ArrayList<>(), null);
        }

        Long newTimestamp = orderList.get(orderList.size() - 1).getUpdateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        List<OrderDTO> list = orderList.stream().map(this::convertOrderToDTO).toList();

        return new PageBean<>(list, newTimestamp);
    }


    /**
     * 将order对象转换成orderDTO对象
     *
     * @param order order对象
     * @return orderDTO对象
     */
    public OrderDTO convertOrderToDTO(Order order) {
        // 先获取商品信息
        Sku sku = skuService.getById(order.getSkuId());

        // 再获取产品信息
        Spu spu = spuService.getById(sku.getSpuId());

        OrderDTO orderDTO = MyBeanUtils.copyProperties(order, OrderDTO.class);

        orderDTO.setOrderId(order.getId());
        orderDTO.setTitle(spu.getTitle());
        orderDTO.setImage(spu.getImage());
        orderDTO.setSpec(sku.getSpec());

        return orderDTO;
    }
}
