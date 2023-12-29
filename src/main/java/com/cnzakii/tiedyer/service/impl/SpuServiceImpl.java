package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.Spu;
import com.cnzakii.tiedyer.mapper.SpuMapper;
import com.cnzakii.tiedyer.model.dto.PageBean;
import com.cnzakii.tiedyer.model.dto.shop.SpuDTO;
import com.cnzakii.tiedyer.model.dto.shop.SpuSpecDTO;
import com.cnzakii.tiedyer.service.SpuService;
import com.cnzakii.tiedyer.service.SpuSpecService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品表-spu 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-12
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private SpuSpecService specService;


    /**
     * 根据spuId获取SpuDTO对象
     *
     * @param spuId 产品Id
     * @return SpuDTO对象
     */
    @Override
    public SpuDTO getSpuDTObySpuId(Long spuId) {
        Spu spu = spuMapper.selectById(spuId);
        return (spu == null) ? null : convertSpuToDTO(spu);

    }

    /**
     * 获取推荐列表
     *
     * @param timestamp 限制时间戳
     * @param pageSize  限制个数
     * @return 分页查询结果
     */
    @Override
    public PageBean<Spu> getSpuResultByRecommend(Long timestamp, Integer pageSize) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        List<Spu> spuList = spuMapper.selectRecommendList(dateTime, pageSize);

        return getSpuPageBean(spuList);
    }

    /**
     * 根据分类id获取Spu列表
     *
     * @param categoryId 分类id
     * @param timestamp  限制时间戳
     * @param pageSize   限制个数
     * @return 分页查询结果
     */
    @Override
    public PageBean<Spu> getSpuResultByCategory(Integer categoryId, Long timestamp, Integer pageSize) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        List<Spu> spuList = spuMapper.selectListByCategory(categoryId, dateTime, pageSize);

        return getSpuPageBean(spuList);
    }

    /**
     * 全文检索
     *
     * @param key       检索关键词
     * @param timestamp 限制时间戳,默认为当前时间戳
     * @param pageSize  限制个数，默认为5
     * @return 分页查询结果
     */
    @Override
    public PageBean<Spu> getSpuResultByFullTextSearch(String key, Long timestamp, Integer pageSize) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        List<Spu> spuList = spuMapper.selectListByFullTextSearch(key, dateTime, pageSize);
        return getSpuPageBean(spuList);
    }

    /**
     * 获取pageBean对象
     *
     * @param spuList 查询结果列表
     * @return pageBean对象
     */
    private PageBean<Spu> getSpuPageBean(List<Spu> spuList) {
        if (CollectionUtils.isEmpty(spuList)) {
            // 如果为空，则直接返回空对象
            return new PageBean<>(new ArrayList<>(), null);
        }

        Long newTimestamp = spuList.get(spuList.size() - 1).getCreateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return new PageBean<>(spuList, newTimestamp);
    }


    /**
     * 将Spu对象转化成SpuDTO对象
     *
     * @param spu Spu对象
     * @return SpuDTO对象
     */
    public SpuDTO convertSpuToDTO(Spu spu) {
        List<SpuSpecDTO> specList = null;
        if (spu.getUseSpec() == 1) {
            // 获取规格参数
            specList = specService.getSpecListBySpuId(spu.getId());
        }

        SpuDTO spuDTO = MyBeanUtils.copyProperties(spu, SpuDTO.class);

        spuDTO.setSpec(specList);
        spuDTO.setSpuId(spu.getId());

        return spuDTO;
    }


}
