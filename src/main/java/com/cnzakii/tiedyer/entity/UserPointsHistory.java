package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户积分获取/消耗记录表
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user_points_history")
public class UserPointsHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 5141973718822958741L;

    /**
     * 积分记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 获取/消耗的积分
     */
    private Integer points;

    /**
     * 描述
     */
    private String description;

    /**
     * 记录时间
     */
    private LocalDateTime createTime;
}
