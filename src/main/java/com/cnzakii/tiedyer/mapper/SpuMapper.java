package com.cnzakii.tiedyer.mapper;

import com.cnzakii.tiedyer.entity.Spu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

    public Goods[] getInforByLable();
}
