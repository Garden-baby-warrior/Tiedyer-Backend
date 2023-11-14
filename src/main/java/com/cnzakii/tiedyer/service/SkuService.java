package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Sku;
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
     * 将Sku转化成SkuDTO对象
     *
     * @param sku sku
     * @return SkuDTO对象
     */
    SkuDTO convertSkuToDTO(Sku sku);

}
