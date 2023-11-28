package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 产品表-spu
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_spu")
public class Spu implements Serializable {

    @Serial
    private static final long serialVersionUID = -3291453091544356596L;

    /**
     * 主键
     */
    @JsonProperty("spuId")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分类id
     */
    @JsonProperty("categoryId")
    private Integer categoryId;

    /**
     * 标题
     */
    @JsonProperty("title")
    private String title;

    /**
     * 图片
     */
    @JsonProperty("image")
    private String image;

    /**
     * 产品起售价
     */
    @JsonProperty("basePrice")
    private BigDecimal basePrice;

    /**
     * 产品销量
     */
    @JsonProperty("sales")
    private Integer sales;

    /**
     * 产地
     */
    @JsonProperty("productionPlace")
    private String productionPlace;

    /**
     * 是否有规格，0为单一规格，1为多规格
     */
    @JsonIgnore
    private Integer useSpec;

    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 逻辑删除
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
