package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Spu;
import com.cnzakii.tiedyer.model.dto.shop.SpuDTO;

/**
 * <p>
 * 产品表-spu 服务类
 * </p>
 *
 * @author xyt
 * @since 2023-11-12
 */
public interface SpuService extends IService<Spu> {


    /**
     * 根据spuId获取SpuDTO对象
     *
     * @param spuId 产品Id
     * @return SpuDTO对象
     */
    SpuDTO getSpuDTObySpuId(Long spuId);


}
