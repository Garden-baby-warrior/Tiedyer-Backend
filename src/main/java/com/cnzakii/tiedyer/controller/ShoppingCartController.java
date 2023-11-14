package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Sku;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.model.dto.shop.CommodityDTO;
import com.cnzakii.tiedyer.model.request.shop.DeleteCommodityRequest;
import com.cnzakii.tiedyer.model.request.shop.ShoppingInfoRequest;
import com.cnzakii.tiedyer.service.ShoppingCartService;
import com.cnzakii.tiedyer.service.SkuService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车接口
 *
 * @author Zaki
 * @since 2023-11-09
 **/
@RestController
@RequestMapping("/shopping/cart")
public class ShoppingCartController {

    @Resource
    private SkuService skuService;

    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * 将商品保存进购物车
     *
     * @param request 购物信息添加请求
     * @return success
     */
    @PostMapping("/add/commodity")
    public ResponseResult<String> addCommodity(@Validated @RequestBody ShoppingInfoRequest request) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        // 检查skuId是否存在
        Sku sku = skuService.getById(request.getSkuId());
        if (sku == null) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "商品不存在");
        }

        shoppingCartService.saveCommodity(userId, sku.getId(), request.getNum());

        return ResponseResult.success();
    }


    /**
     * 更新预选商品的的数量
     *
     * @param request 购物信息添加请求
     * @return success
     */
    @PutMapping("/commodity/num")
    public ResponseResult<String> updateCommodityNum(@Validated @RequestBody ShoppingInfoRequest request) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        // 检查skuId是否存在
        Sku sku = skuService.getById(request.getSkuId());
        if (sku == null) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "商品不存在");
        }

        shoppingCartService.updateCommodityNum(userId, sku.getId(), request.getNum());

        return ResponseResult.success();
    }


    /**
     * 获取用户的购物车列表
     *
     * @return 购物车列表
     */
    @GetMapping("/list")
    public ResponseResult<List<CommodityDTO>> getCommodityList() {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        List<CommodityDTO> list = shoppingCartService.getCommodityListByUserId(userId);

        return ResponseResult.success(list);
    }


    /**
     * 删除购物车中的商品
     *
     * @param request 购物信息删除请求
     * @return success
     */
    @DeleteMapping("/commodity")
    public ResponseResult<String> deleteCommodity(@Validated @RequestBody DeleteCommodityRequest request) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        shoppingCartService.deleteCommodity(userId, request.getSkuIds());

        return ResponseResult.success();
    }


}
