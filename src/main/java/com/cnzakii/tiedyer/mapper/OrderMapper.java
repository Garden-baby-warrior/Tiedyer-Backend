package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询用户订单
     *
     * @param userId        用户ID
     * @param statusCode    订单状态 - 0取消，1未付款、2已付款、3已发货、4已签收
     * @param limitDateTime 限制时间
     * @param limitSize     限制查询个数
     * @return 订单列表
     */
    List<Order> selectOrderList(@Param("userId") Long userId, @Param("limitDateTime") LocalDateTime limitDateTime, @Param("limitSize") Integer limitSize, @Param("statusCode") Integer... statusCode);
}
