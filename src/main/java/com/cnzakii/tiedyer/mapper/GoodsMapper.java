package com.cnzakii.tiedyer.mapper;


/**
 * <p>
 *    商品信息 Mapper 接口
 * </p>
 *
 * @auther xyt
 * @since 2023-11-06
 */

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 标签获取商品信息
     *
     * @param lable 商品标签
     * @return 商品
     */
    Goods[] getGoodsImformationByLable(@Param("lable") String lable);

    /**
     * 搜索获取商品信息
     *
     * @param goodsKey key
     * @return 商品数组
     */
    Goods[] getGoodsImformationByKey(@Param("goodsKey") String key);
}