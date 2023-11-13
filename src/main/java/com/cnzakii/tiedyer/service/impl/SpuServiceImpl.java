package com.cnzakii.tiedyer.service.impl;

import com.cnzakii.tiedyer.entity.Spu;
import com.cnzakii.tiedyer.mapper.SpuMapper;
import com.cnzakii.tiedyer.service.SpuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品表-spu 服务实现类
 * </p>
 *
 * @author xyt
 * @since 2023-11-12
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    Goods[] getGoodsByLable(){
        return SpuMapper.selectArrays(new LambdaQueryWrapper<Goods[]>().eq(Goods::getsale));
    }
}
