package com.cnzakii.tiedyer.service;

import java.util.Map;

/**
 * 购物车接口类
 *
 * @author Zaki
 * @since 2023-11-09
 **/
public interface ShoppingCartService {

    /**
     * 将商品保存进购物车
     *
     * @param userId 用户ID
     * @param skuId  商品id
     * @param num    商品数量
     */
    void saveCommodity(Long userId, Long skuId, Integer num);


}
