package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.model.dto.game.AnswerDTO;
import com.cnzakii.tiedyer.model.dto.game.QuestionDTO;
import com.cnzakii.tiedyer.model.request.game.QuestionAnswerRequest;
import com.cnzakii.tiedyer.service.QuestionGameService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品 接口
 *
 * @author xyt
 * @since 2023-11-06
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 标签获取商品信息
     *
     * return 商品信息数组
     */
    @GetMapping("/lables/{lable}")
    public ResponseResult<GoodsDTO[]> getLbaleGoods(@PathVariable("lable") String lable){
        if(lable.equals("recommend")){
            Goods[] goods = SpuService.getGoodsByLable();
        }else {
            Goods[] goods = CategoryService.getGoodsByLable(lable);
        }
        GoodsDTO[] goodsDTO = GoodsDTO[goods.length];
        for(int i = 0;i<goods.length;i++){
            goodsDTO[i] = MyBeanUtils.copyProject(goods[i],GoodsDTO.class);
        }
        return ResponseResult.success(goodsDTO);

    }

    /**
     * 搜索获取商品信息
     *
     * return 商品信息数组
     */

    @GetMapping("/search/{key}")
    public ResponseResult<GoodsDTO[]> getSearchGoods(@PathVariable("key") String key){
        Goods[] goods = GoodsService.getGoodsByKey(key);
        GoodsDTO[] goodsDTOs = GoodsDTO[goods.length];
        for(int i = 0;i<goods.length;i++){
            goodsDTOs[i] = MyBeanUtils.copyProject(goods[i],GoodsDTO.class);
        }
        return ResponseResult.success(goodsDTOs);
    }

}