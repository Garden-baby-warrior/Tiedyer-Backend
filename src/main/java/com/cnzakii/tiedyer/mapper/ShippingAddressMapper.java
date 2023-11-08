package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.ShippingAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zaki
 * @since 2023-11-01
 */
@Mapper
public interface ShippingAddressMapper extends BaseMapper<ShippingAddress> {

    /**
     * 实现范围内的优先级自增或者自减
     *
     * @param userId 用户id
     * @param value 优先级变化的数值
     * @param start  起始优先级
     * @param end    结束优先级
     */
    void updatePrioritiesInRange(@Param("userId") Long userId, @Param("value")Integer value , @Param("start") Integer start, @Param("end") Integer end);


}
