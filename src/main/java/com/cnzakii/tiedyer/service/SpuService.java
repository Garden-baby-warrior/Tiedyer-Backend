package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.Spu;
import com.cnzakii.tiedyer.model.dto.PageBean;
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

    /**
     * 获取推荐列表
     *
     * @param timestamp 限制时间戳
     * @param pageSize  限制个数
     * @return 分页查询结果
     */
    PageBean<Spu> getSpuResultByRecommend(Long timestamp, Integer pageSize);

    /**
     * 根据分类id获取Spu列表
     *
     * @param categoryId 分类id
     * @param timestamp  限制时间戳
     * @param pageSize   限制个数
     * @return 分页查询结果
     */
    PageBean<Spu> getSpuResultByCategory(Integer categoryId, Long timestamp, Integer pageSize);

    /**
     * 全文检索
     *
     * @param key       检索关键词
     * @param timestamp 限制时间戳,默认为当前时间戳
     * @param pageSize  限制个数，默认为5
     * @return 分页查询结果
     */
    PageBean<Spu> getSpuResultByFullTextSearch(String key, Long timestamp, Integer pageSize);
}
