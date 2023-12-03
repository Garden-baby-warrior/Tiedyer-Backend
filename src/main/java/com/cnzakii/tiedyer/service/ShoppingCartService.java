package com.cnzakii.tiedyer.service;

import com.cnzakii.tiedyer.model.dto.order.OrderReceiptDTO;
import com.cnzakii.tiedyer.model.dto.shop.PreSelectedCommodityDTO;

import java.util.List;

/**
 * 购物车接口类
 *
 * @author Zaki
 * @since 2023-11-09
 **/
public interface ShoppingCartService {

    /**
     * 将商品保存进购物车或者添加商品数量
     *
     * @param userId 用户ID
     * @param skuId  商品id
     * @param num    商品数量
     */
    void saveCommodity(Long userId, Long skuId, Integer num);


    /**
     * 更新预选商品的的数量
     *
     * @param userId 用户ID
     * @param skuId  商品id
     * @param num    商品数量
     */
    void updateCommodityNum(Long userId, Long skuId, Integer num);


    /**
     * 查询用户购物车信息
     *
     * @param userId 用户id
     * @return list
     */
    List<PreSelectedCommodityDTO> getCommodityListByUserId(Long userId);


    /**
     * 批量创建订单
     *
     * @param userId 用户id
     * @param skuIds 商品id
     * @return 订单回执
     */
    OrderReceiptDTO creatOrderList(Long userId, Long[] skuIds);


    /**
     * 删除购物车中的商品
     *
     * @param userId 用户ID
     * @param skuIds 商品id列表
     */
    void deleteCommodity(Long userId, Long... skuIds);


}
