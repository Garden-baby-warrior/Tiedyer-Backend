package com.cnzakii.tiedyer.model.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息的数据传输对象
 *
 * @author Zaki
 * @since 2023-09-05
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7363463943850848619L;

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户头像地址
     */
    private String avatarPath;

    /**
     * 用户游戏积分
     */
    private Integer points;

}
