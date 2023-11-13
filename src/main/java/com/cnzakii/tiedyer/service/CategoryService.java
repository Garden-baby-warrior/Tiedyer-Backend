package com.cnzakii.tiedyer.service;

import com.cnzakii.tiedyer.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 产品分类表 服务类
 * </p>
 *
 * @author xyt
 * @since 2023-11-12
 */
public interface CategoryService extends IService<Category> {

    Goods[] getGoodsByLable(String lable);
}
