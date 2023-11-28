package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 产品分类表
 * </p>
 *
 * @author zaki
 * @since 2023-11-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_category")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 2167023694836088726L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 优先级
     */
    private Integer priority;
}
