package com.cnzakii.tiedyer.model.dto.game;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 商品数据传输对象
 *
 * @author xyt
 * @since 2023-11-06
 **/
@Data
public class GoodsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1546755776919647976L;

    /**
     * 商品id
     */
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