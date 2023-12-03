package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.Spu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 产品表-spu Mapper 接口
 * </p>
 *
 * @author xyt
 * @since 2023-11-12
 */
@Mapper
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 获取推荐列表，按照销量降序
     *
     * @param limitDateTime 限制时间
     * @param limitSize     限制查询个数
     * @return spu列表
     */
    List<Spu> selectRecommendList(@Param("limitDateTime") LocalDateTime limitDateTime, @Param("limitSize") Integer limitSize);

    /**
     * 根据分类ID获取Spu列表，按照销量降序
     *
     * @param limitDateTime 限制时间
     * @param limitSize     限制查询个数
     * @return spu列表
     */
    List<Spu> selectListByCategory(@Param("categoryId") Integer categoryId, @Param("limitDateTime") LocalDateTime limitDateTime, @Param("limitSize") Integer limitSize);

    /**
     * 全文检索
     *
     * @param key           检索关键词
     * @param limitDateTime 限制时间
     * @param limitSize     限制查询个数
     * @return spu列表
     */
    List<Spu> selectListByFullTextSearch(@Param("key") String key, @Param("limitDateTime") LocalDateTime limitDateTime, @Param("limitSize") Integer limitSize);
}
