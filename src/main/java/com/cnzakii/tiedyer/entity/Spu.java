package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分类id
     */
    private Long categoryId;

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
     * 是否有规格，0为单一规格，1为多规格
     */
    private Integer useSpec;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 逻辑删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
