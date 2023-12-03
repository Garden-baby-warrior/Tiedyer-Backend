package com.cnzakii.tiedyer.service.impl;

import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Sku;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.model.dto.order.OrderReceiptDTO;
import com.cnzakii.tiedyer.model.dto.shop.PreSelectedCommodityDTO;
import com.cnzakii.tiedyer.model.dto.shop.SkuDTO;
import com.cnzakii.tiedyer.model.dto.shop.SpuDTO;
import com.cnzakii.tiedyer.service.OrderService;
import com.cnzakii.tiedyer.service.ShoppingCartService;
import com.cnzakii.tiedyer.service.SkuService;
import com.cnzakii.tiedyer.service.SpuService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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

    @Resource
    private SpuService spuService;

    @Resource
    private OrderService orderService;

    /**
     * 将商品保存进购物车-累加商品数量
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
        // 如果存在，则累加
        if (contains) {
            num += Integer.parseInt((String) map.get(skuStr));
        }

        // 查看库存是否充足
        Sku sku = skuService.getById(skuId);
        if (num > sku.getStock()) {
            throw new BusinessException(ResponseStatus.FAIL, "库存不足");
        }


        // 存入redis中
        stringRedisTemplate.opsForHash().put(key, skuStr, String.valueOf(num));
    }

    /**
     * 更新预选商品的的数量,直接更新商品数量
     *
     * @param userId 用户ID
     * @param skuId  商品id
     * @param num    商品数量
     */
    @Override
    public void updateCommodityNum(Long userId, Long skuId, Integer num) {
        String skuStr = String.valueOf(skuId);
        String key = USER_SHOPPING_CART + userId;
        // 先尝试获取该用户的购物车信息
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);

        // 检查是否已经存在该商品
        boolean contains = map.containsKey(skuStr);
        // 如果不存在，则抛出异常
        if (!contains) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "购物车不存在该商品");
        }

        // 查看库存是否充足
        Sku sku = skuService.getById(skuId);
        if (num > sku.getStock()) {
            throw new BusinessException(ResponseStatus.FAIL, "库存不足");
        }

        // 存入redis中
        stringRedisTemplate.opsForHash().put(key, skuStr, String.valueOf(num));
    }

    /**
     * 查询用户购物车信息
     *
     * @param userId 用户id
     * @return list
     */
    @Override
    public List<PreSelectedCommodityDTO> getCommodityListByUserId(Long userId) {
        String key = USER_SHOPPING_CART + userId;

        // 先尝试获取该用户的购物车信息
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);

        Set<Object> skuSet = map.keySet();
        List<PreSelectedCommodityDTO> result = new ArrayList<>(skuSet.size());
        for (Object skuId : skuSet) {
            String skuStr = String.valueOf(skuId);
            // 获取商品信息
            Sku sku = skuService.getById(skuStr);
            SkuDTO skuDTO = skuService.convertSkuToDTO(sku);
            // 获取产品信息
            SpuDTO spuDTO = spuService.getSpuDTObySpuId(sku.getSpuId());
            // 组装成PreSelectedCommodityDTO
            PreSelectedCommodityDTO preSelectedCommodityDTO = new PreSelectedCommodityDTO(spuDTO, skuDTO, Integer.parseInt((String) map.get(skuStr)));
            result.add(preSelectedCommodityDTO);
        }

        return result;
    }

    /**
     * 批量创建订单
     *
     * @param userId 用户id
     * @param skuIds 商品id
     * @return 订单回执
     */
    @Override
    public OrderReceiptDTO creatOrderList(Long userId, Long[] skuIds) {
        String key = USER_SHOPPING_CART + userId;
        // 先尝试获取该用户的购物车信息
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);

        List<OrderReceiptDTO> list = new ArrayList<>();

        for (Long skuId : skuIds) {
            // 先获取商品数量
            int num = Integer.parseInt((String) map.get(String.valueOf(skuId)));

            if (num == 0) {
                throw new BusinessException(ResponseStatus.REQUEST_ERROR, "购物车中不存在该商品");
            }

            // 创建订单
            OrderReceiptDTO order = orderService.createOrder(userId, skuId, num);
            list.add(order);
            // 删除购物车中的商品信息
            stringRedisTemplate.opsForHash().delete(key, String.valueOf(skuId));
        }

        List<String> orderIds = new ArrayList<>(list.size());
        BigDecimal amount = BigDecimal.valueOf(0);

        for (OrderReceiptDTO receiptDTO : list) {
            orderIds.add(receiptDTO.getOrderId()[0]);
            amount = amount.add(receiptDTO.getAmount());
        }

        return new OrderReceiptDTO(orderIds.toArray(String[]::new), amount);
    }

    /**
     * 删除购物车中的商品
     *
     * @param userId 用户ID
     * @param skuIds 商品id列表
     */
    @Override
    public void deleteCommodity(Long userId, Long... skuIds) {
        String key = USER_SHOPPING_CART + userId;

        String[] stringSkuIds = Arrays.stream(skuIds)
                .map(String::valueOf)
                .toArray(String[]::new);

        // 删除商品信息
        stringRedisTemplate.opsForHash().delete(key, (Object[]) stringSkuIds);

    }


}
