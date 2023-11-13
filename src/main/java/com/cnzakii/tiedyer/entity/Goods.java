package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 商品
 *
 * @author xyt
 * @since 2023-11-06
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_goods")
public class Goods implements Serializable {

    @Serial
    private static final long serialVersionUID = -1469763746150894689L;

    /**
     * 商品Id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private int id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 生产地
     */
    private String productionPlace;

    /**
     * 图片地址
     */
    private String pictureAddress;

    /**
     * 销量
     */
    private int sales;

    /**
     * 价格
     */
    private double price;

    /**
     * 码数
     */
    private String yardage;

    /**
     * 标签
     */
    private String lable;


}