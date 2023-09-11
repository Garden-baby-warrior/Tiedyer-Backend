package com.cnzakii.tiedyer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户身份验证数据传输对象<br/>
 * 包括用户非敏感信息和用户Token
 *
 * @author Zaki
 * @since 2023-09-04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2561235615046750189L;

    // 访问令牌（Token）字符串，用于身份验证
    private String authentication;

    // 用户信息的数据传输对象
    private UserDTO userInfo;

}