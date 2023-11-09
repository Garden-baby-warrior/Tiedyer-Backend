package com.cnzakii.tiedyer.service.impl;

import com.cnzakii.tiedyer.entity.Category;
import com.cnzakii.tiedyer.mapper.CategoryMapper;
import com.cnzakii.tiedyer.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品分类表 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
