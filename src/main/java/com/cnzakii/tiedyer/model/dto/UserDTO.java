package com.cnzakii.tiedyer.model.dto;

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
    private Long userId;

    /**
     * 电子邮件,脱敏处理
     */
    private String email;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像地址
     */
    private String avatarPath;

    /**
     * 用户角色： R_USER / R_MERCHANT
     */
    private String role;


}
