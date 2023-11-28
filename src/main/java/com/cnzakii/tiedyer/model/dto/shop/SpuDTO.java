package com.cnzakii.tiedyer.model.dto.shop;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 产品信息数据传输对象
 *
 * @author Zaki
 * @since 2023-11-14
 **/
@Data
public class SpuDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1977317166636604520L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long spuId;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String image;

    /**
     * 产品起售价
     */
    private BigDecimal basePrice;

    /**
     * 产品销量
     */
    private Integer sales;

    /**
     * 产地
     */
    private String productionPlace;

    /**
     * 是否有规格，0为单一规格，1为多规格
     */
    private Integer useSpec;

    /**
     * 参数列表
     */
    private List<SpuSpecDTO> spec;
}
