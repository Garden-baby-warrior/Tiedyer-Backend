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
 * 徽章表
 * </p>
 *
 * @author zaki
 * @since 2023-11-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_badge")
public class Badge implements Serializable {

    @Serial
    private static final long serialVersionUID = 5500099219309655970L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 徽章名
     */
    private String name;


    /**
     * 彩色头像地址-用户获得时显示
     */
    private String colorImage;


    /**
     * 灰色头像地址-用户未获得时显示
     */
    private String greyImage;

    /**
     * 徽章兑换需要的积分
     */
    private Integer points;
}
