package com.cnzakii.tiedyer.service.impl;

import com.cnzakii.tiedyer.entity.Order;
import com.cnzakii.tiedyer.mapper.OrderMapper;
import com.cnzakii.tiedyer.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
