package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.Spu;
import com.cnzakii.tiedyer.mapper.SpuMapper;
import com.cnzakii.tiedyer.model.dto.shop.SpuDTO;
import com.cnzakii.tiedyer.model.dto.shop.SpuSpecDTO;
import com.cnzakii.tiedyer.service.SpuService;
import com.cnzakii.tiedyer.service.SpuSpecService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品表-spu 服务实现类
 * </p>
 *
 * @author xyt
 * @since 2023-11-12
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private SpuSpecService specService;

    //    Goods[] getGoodsByLable(){
//        return SpuMapper.selectArrays(new LambdaQueryWrapper<Goods[]>().eq(Goods::getsale));
//    }


    /**
     * 根据spuId获取SpuDTO对象
     *
     * @param spuId 产品Id
     * @return SpuDTO对象
     */
    @Override
    public SpuDTO getSpuDTObySpuId(Long spuId) {
        Spu spu = spuMapper.selectById(spuId);
        if (spu == null) {
            return null;
        }
        List<SpuSpecDTO> specList = null;
        if (spu.getUseSpec() == 1) {
            // 获取规格参数
            specList = specService.getSpecListBySpuId(spuId);
        }
        return convertSpuToDTO(spu, specList);
    }


    /**
     * 将Spu对象转化成SpuDTO对象
     *
     * @param spu      Spu对象
     * @param specList Spu对象的规格，当useSpec为0时为null即可
     * @return SpuDTO对象
     */
    public SpuDTO convertSpuToDTO(Spu spu, List<SpuSpecDTO> specList) {
        SpuDTO spuDTO = MyBeanUtils.copyProperties(spu, SpuDTO.class);

        spuDTO.setSpec(specList);
        spuDTO.setSpuId(spu.getId());

        return spuDTO;
    }


}
