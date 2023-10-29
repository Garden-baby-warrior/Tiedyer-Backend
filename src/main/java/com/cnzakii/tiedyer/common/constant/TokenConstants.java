package com.cnzakii.tiedyer.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * token 常量类
 *
 * @author Zaki
 * @since 2023-10-28
 **/
@Component
public class TokenConstants {

    // token名字，包括redis的前缀，请求头读取的属性名
    public static String TOKEN_NAME;

    // RefreshToken的映射：  RefreshToken-->userId
    public static String REFRESH_TOKEN;

    // RefreshToken的映射：userId-->RefreshToken
    public static String REFRESH_TOKEN_REVERSE;

    // RefreshToken过期时间,默认60天
    public static Long REFRESH_TOKEN_TTL;

    // JWT 签名密钥
    public static String JWT_SECRET;

    // JWT 过期时间(秒)，默认2小时
    public static Long JWT_EXP;


    @Value("${my-token.token-name:Authorization}")
    public void setTokenName(String tokenName) {
        TOKEN_NAME = tokenName;
    }

    @Value("${my-token.token-name:Authorization}")
    public void setRefreshToken(String tokenName) {
        REFRESH_TOKEN = tokenName + ":refresh:";
    }

    @Value("${my-token.token-name:Authorization}")
    public void setRefreshTokenList(String tokenName) {
        REFRESH_TOKEN_REVERSE = tokenName + ":map:refresh:";
    }


    @Value("${my-token.refresh-token.exp:5184000}")
    public void setRefreshTokenTtl(Long refreshTokenTtl) {
        REFRESH_TOKEN_TTL = refreshTokenTtl;
    }

    @Value("${my-token.jwt.exp:7200}")
    public void setJwtExp(Long jwtExp) {
        JWT_EXP = jwtExp;
    }

    /**
     * SECRET 是签名密钥，只生成一次即可，生成方法：
     * SecretKey secretKey = Jwts.SIG.HS256.key().build();
     * System.out.println(Encoders.BASE64.encode(secretKey.getEncoded()));
     *
     * @param jwtSecret 签名密钥
     */
    @Value("${my-token.jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }

}
