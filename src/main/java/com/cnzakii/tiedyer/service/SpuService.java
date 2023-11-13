package com.cnzakii.tiedyer.service;

import com.cnzakii.tiedyer.entity.Spu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 产品表-spu 服务类
 * </p>
 *
 * @author xyt
 * @since 2023-11-12
 */
public interface SpuService extends IService<Spu> {

    Goods[] getGoodsByLable();
}
