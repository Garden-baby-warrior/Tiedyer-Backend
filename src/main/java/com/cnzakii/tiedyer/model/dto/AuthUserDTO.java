package com.cnzakii.tiedyer.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
public class AuthUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2561235615046750189L;

    // 用户Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String role;

    // 是否是新用户
    private boolean newUser;

    // 刷新令牌（Token）字符串
    private String refreshToken;

    // 访问令牌（Token）字符串
    private String accessToken;


    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role);
        authorities.add(auth);
        return authorities;
    }

}