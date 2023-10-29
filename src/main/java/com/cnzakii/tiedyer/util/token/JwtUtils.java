package com.cnzakii.tiedyer.util.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.cnzakii.tiedyer.common.constant.TokenConstants.JWT_EXP;
import static com.cnzakii.tiedyer.common.constant.TokenConstants.JWT_SECRET;


/**
 * 本工具类是对的封装<br/>
 * 用于JWT的创建，验证等操作
 *
 * @author Zaki
 * @since 2023-10-28
 **/
@Slf4j
public class JwtUtils {

    private static final String USER_ID = "userId";
    private static final String ROLE = "role";

    // 时钟偏差，60秒
    private static final long CLOCK_SKEW_SECONDS = 60;


    /**
     * 对JWT进行校验，包括是否篡改，能否正确解析内容，以及是否过期
     *
     * @param jwtStr JWT字符串
     * @return JWT是否有效
     */
    public static boolean isTokenValid(String jwtStr) {
        try {
            // 尝试解析jwtStr
            getPayLoad(jwtStr);
            // 如果没有没抛出异常，则返回true
            return true;
        } catch (JwtException e) {
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * 解析JWT,获取userId<br/>
     * 在执行该步骤前，请先执行<code>isTokenValid</code>方法
     *
     * @param jwtStr JWT字符串
     * @return userId
     */
    public static String getUserId(String jwtStr) {
        return (String) getPayLoad(jwtStr).get(USER_ID);
    }

    /**
     * 解析JWT,获取role<br/>
     * 在执行该步骤前，请先执行<code>isTokenValid</code>方法
     *
     * @param jwtStr JWT字符串
     * @return role
     */
    public static String getAuthentication(String jwtStr) {
        return (String) getPayLoad(jwtStr).get(ROLE);
    }


    /**
     * 解析JWT，并获取其中的有效载荷<br/>
     * 在执行该步骤前，请先执行<code>isTokenValid</code>方法
     *
     * @param jwtStr JWT字符串
     * @return 有效载荷
     */
    public static Claims getPayLoad(String jwtStr) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .clockSkewSeconds(CLOCK_SKEW_SECONDS)
                .build()
                .parseSignedClaims(jwtStr)
                .getPayload();
    }

    /**
     * 创建JWT
     *
     * @param userId 用户id
     * @param role   用户角色
     * @return JWT字符串
     */
    public static String createToken(String userId, String role) {
        // 获取当前时间
        Date now = new Date();
        // 设置私人声明
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put(USER_ID, userId);
        claims.put(ROLE, role);
        // 构建JWT
        JwtBuilder jwtBuilder = Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claims(claims)// 设置私人声明
                .issuedAt(now) // 签发时间
                .expiration(new Date(now.getTime() + JWT_EXP * 1000)) // 过期时间
                .signWith(getSigningKey());// 设置签名密钥
        // 返回JWT字符串
        return jwtBuilder.compact();
    }


    /**
     * SecretKey 根据 SECRET 的编码方式解码后得到：
     * Base64 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
     * Base64URL 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));
     * 未编码：SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
     *
     * @return 签名密钥
     */
    private static SecretKey getSigningKey() {
        byte[] encodeKey = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(encodeKey);
    }
}
