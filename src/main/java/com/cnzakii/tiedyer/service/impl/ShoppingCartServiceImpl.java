package com.cnzakii.tiedyer.service.impl;

import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Sku;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.service.ShoppingCartService;
import com.cnzakii.tiedyer.service.SkuService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.cnzakii.tiedyer.common.constant.RedisConstants.USER_SHOPPING_CART;

/**
 * 购物车实现类
 *
 * @author Zaki
 * @since 2023-11-09
 **/
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SkuService skuService;

    /**
     * 将商品保存进购物车
     *
     * @param userId 用户ID
     * @param skuId  商品id
     * @param num    商品数量
     */
    @Override
    public void saveCommodity(Long userId, Long skuId, Integer num) {
        String skuStr = String.valueOf(skuId);
        String key = USER_SHOPPING_CART + userId;

        // 先尝试获取该用户的购物车信息
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);

        // 检查是否已经存在该商品
        boolean contains = map.containsKey(skuStr);
        // 如果存在，则累加库存
        if (contains) {
            num += Integer.parseInt((String) map.get(skuStr));
        }
        // 查看库存是否充足
        Sku sku = skuService.getById(skuId);
        if (num > sku.getStock()) {
            throw new BusinessException(ResponseStatus.FAIL, "库存不足");
        }

        // 存入redis中
        stringRedisTemplate.opsForHash().put(key, skuStr, num);
    }


}
