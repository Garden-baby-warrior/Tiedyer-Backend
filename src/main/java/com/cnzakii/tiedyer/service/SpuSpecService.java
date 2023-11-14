package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.SpuSpec;
import com.cnzakii.tiedyer.model.dto.shop.SpuSpecDTO;

import java.util.List;

/**
 * <p>
 * 产品规格表 服务类
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
public interface SpuSpecService extends IService<SpuSpec> {

    /**
     * 根据spuId查询对应规格列表
     *
     * @param spuId 产品id
     * @return 规格列表
     */
    List<SpuSpecDTO> getSpecListBySpuId(Long spuId);

}
