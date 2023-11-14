package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
