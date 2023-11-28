package com.cnzakii.tiedyer.model.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 商品详情对象
 *
 * @author Zaki
 * @since 2023-11-28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommodityDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = -8239232686586073087L;

    /**
     * 产品信息
     */
    private SpuDTO base;

    /**
     * 商品详情信息
     */
    private SkuDTO[] details;

}
