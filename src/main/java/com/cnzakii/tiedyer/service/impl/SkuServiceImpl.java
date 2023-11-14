package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.Sku;
import com.cnzakii.tiedyer.mapper.SkuMapper;
import com.cnzakii.tiedyer.model.dto.shop.SkuDTO;
import com.cnzakii.tiedyer.service.SkuService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表-sku 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {



    /**
     * 将Sku转化成SkuDTO对象
     *
     * @param sku sku
     * @return SkuDTO对象
     */
    @Override
    public SkuDTO convertSkuToDTO(Sku sku) {
        SkuDTO skuDTO = MyBeanUtils.copyProperties(sku, SkuDTO.class);
        skuDTO.setSkuId(sku.getId());
        return skuDTO;
    }
}
