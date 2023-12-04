package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.SpuSpec;
import com.cnzakii.tiedyer.mapper.SpuMapper;
import com.cnzakii.tiedyer.mapper.SpuSpecMapper;
import com.cnzakii.tiedyer.model.dto.shop.SpuSpecDTO;
import com.cnzakii.tiedyer.service.SpuSpecService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品规格表 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Service
public class SpuSpecServiceImpl extends ServiceImpl<SpuSpecMapper, SpuSpec> implements SpuSpecService {

    @Resource
    private SpuSpecMapper specMapper;

    @Resource
    private SpuMapper spuMapper;

    /**
     * 根据spuId查询对应规格列表
     *
     * @param spuId 产品id
     * @return 规格列表
     */
    @Override
    public List<SpuSpecDTO> getSpecListBySpuId(Long spuId) {
        List<SpuSpec> spuSpecList = specMapper.selectList(new LambdaQueryWrapper<SpuSpec>().eq(SpuSpec::getSpuId, spuId));


        if (CollectionUtils.isEmpty(spuSpecList)) {
            return null;
        }
        // 转化成SpuSpecDTO
        List<SpuSpecDTO> result = new ArrayList<>(spuSpecList.size());
        for (SpuSpec spec : spuSpecList) {
            SpuSpecDTO spuSpecDTO = MyBeanUtils.copyProperties(spec, SpuSpecDTO.class);
            spuSpecDTO.setValue(spec.getValue().split(","));
            result.add(spuSpecDTO);
        }
        return result;
    }

    /**
     *  增加销量
     * @param spuId 产品id
     * @param num   销售的数量
     */
    @Override
    public void increaseSale(Long spuId, Integer num) {

        spuMapper.increaseSale(spuId,num);
    }


}
