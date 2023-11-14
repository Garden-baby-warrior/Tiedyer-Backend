package com.cnzakii.tiedyer.model.request.shop;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除商品请求，用于购物车删除商品
 *
 * @author Zaki
 * @since 2023-11-14
 **/
@Data
public class DeleteCommodityRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -1768590821490650575L;

    /**
     * skuId 列表
     */
    @NotNull(message = "删除列表不能为空")
    private Long[] SkuIds;
}
