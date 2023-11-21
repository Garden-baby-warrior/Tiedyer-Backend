package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品表-sku Mapper 接口
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Mapper
public interface SkuMapper extends BaseMapper<Sku> {

    /**
     * 减少库存
     *
     * @param skuId  商品ID
     * @param change 变化的库存数量
     */
    int decreaseStock(@Param("skuId") Long skuId, @Param("change") Integer change);

    /**
     * 增加库存
     *
     * @param skuId  商品ID
     * @param change 变化的库存数量
     */
    int increaseStock(@Param("skuId") Long skuId, @Param("change") Integer change);
}
