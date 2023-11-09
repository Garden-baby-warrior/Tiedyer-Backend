package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Sku;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.model.dto.shop.InsertShoppingInfoRequest;
import com.cnzakii.tiedyer.service.ShoppingCartService;
import com.cnzakii.tiedyer.service.SkuService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/add")
    public ResponseResult<String> addCommodity(@Validated @RequestBody InsertShoppingInfoRequest request) {
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

}
