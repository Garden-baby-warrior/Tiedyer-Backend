package com.cnzakii.tiedyer.model.dto.shop;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

/**
 * 购物信息添加请求，用于将商品加入购物车以及添加新订单请求
 *
 * @author Zaki
 * @since 2023-11-09
 **/
@Data
public class InsertShoppingInfoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8756227730899732933L;

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 购买的商品数量
     */
    private Integer num;

}
