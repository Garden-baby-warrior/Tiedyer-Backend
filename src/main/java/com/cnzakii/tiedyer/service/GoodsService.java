package com.cnzakii.tiedyer.service;

/**
 * goods服务接口
 *
 * @author xyt
 * @since 2023-11-06
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 搜索获取商品信息
     *
     * @param key
     * @return 商品数组
     */
    Goods getGoodsByKey(String key);

}