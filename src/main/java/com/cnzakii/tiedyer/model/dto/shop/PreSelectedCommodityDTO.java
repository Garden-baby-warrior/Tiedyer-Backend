package com.cnzakii.tiedyer.model.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 商品信息数据传输体，用于购物车商品信息，订单的商品信息
 *
 * @author Zaki
 * @since 2023-11-14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreSelectedCommodityDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 952927581455445809L;

    /**
     * 产品id
     */
    private SpuDTO base;

    /**
     * 商品id
     */
    private SkuDTO detail;

    /**
     * 购买的商品数量
     */
    private Integer num;

}
