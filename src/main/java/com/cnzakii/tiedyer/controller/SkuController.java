package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.model.dto.shop.CommodityDetail;
import com.cnzakii.tiedyer.service.SkuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品详情接口
 *
 * @author zaki
 * @since 2023-11-09
 */
@RestController
@RequestMapping("/commodity/detail")
public class SkuController {

    @Resource
    private SkuService skuService;


    /**
     * 根据spuId获取商品详情
     *
     * @param spuId spuId
     * @return 商品详情
     */
    @GetMapping("/{spuId}")
    public ResponseResult<CommodityDetail> getCommodityDetail(@PathVariable("spuId") Long spuId) {
        CommodityDetail result = skuService.getCommodityDetail(spuId);
        return ResponseResult.success(result);
    }


}
