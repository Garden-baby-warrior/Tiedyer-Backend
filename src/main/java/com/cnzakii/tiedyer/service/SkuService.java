package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Sku;
import com.cnzakii.tiedyer.model.dto.shop.CommodityDetail;
import com.cnzakii.tiedyer.model.dto.shop.SkuDTO;

/**
 * <p>
 * 商品表-sku 服务类
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
public interface SkuService extends IService<Sku> {

    /**
     * 根据spuId获取商品详情
     *
     * @param spuId spuId
     * @return 商品详情
     */
    CommodityDetail getCommodityDetail(Long spuId);


    /**
     * 减少库存
     *
     * @param skuId  商品ID
     * @param change 变化的库存数量
     */
    void decreaseStock(Long skuId, Integer change);



    /**
     * 增加库存
     *
     * @param skuId  商品ID
     * @param change 变化的库存数量
     */
    void increaseStock(Long skuId, Integer change);


    /**
     * 将Sku转化成SkuDTO对象
     *
     * @param sku sku
     * @return SkuDTO对象
     */
    SkuDTO convertSkuToDTO(Sku sku);



}