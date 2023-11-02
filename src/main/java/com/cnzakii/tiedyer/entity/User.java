package com.cnzakii.tiedyer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * user表映射
 *
 * @author zaki
 * @since 2023-10-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -2914656321689813350L;

    /**
     * 用户Id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 微信小程序：用户openId
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像路径
     */
    private String avatarPath;

    /**
     * 用户游戏积分
     */
    private Integer points;

    /**
     * 用户角色
     */
    private String role;

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
    private Integer isDeleted;


}
