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
 * 用户徽章表 - 记录用户兑换的徽章
 * </p>
 *
 * @author zaki
 * @since 2023-11-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user_badge")
public class UserBadge implements Serializable {

    @Serial
    private static final long serialVersionUID = -93172055754602228L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 徽章id
     */
    private Long badgeId;

    /**
     * 收集时间
     */
    private LocalDateTime collectTime;
}
