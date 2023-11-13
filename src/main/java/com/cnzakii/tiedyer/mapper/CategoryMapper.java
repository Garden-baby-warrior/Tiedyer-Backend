package com.cnzakii.tiedyer.mapper;

import com.cnzakii.tiedyer.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 产品分类表 Mapper 接口
 * </p>
 *
 * @author xyt
 * @since 2023-11-12
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    public Goods[] getInforByLable(String lable);

}
