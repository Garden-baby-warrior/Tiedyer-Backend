package com.cnzakii.tiedyer.service.impl;

import com.cnzakii.tiedyer.service.TokenService;
import com.cnzakii.tiedyer.util.token.JwtUtils;
import com.cnzakii.tiedyer.util.token.SimpleTokenUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.cnzakii.tiedyer.common.constant.TokenConstants.*;


/**
 * Token 接口实现类
 *
 * @author Zaki
 * @since 2023-10-28
 **/
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据userId和role创建accessToken
     *
     * @param userId 用户id
     * @param role   用户角色
     * @return accessToken字符串(JWT)
     */
    @Override
    public String createAccessToken(String userId, String role) {
        // 刷新refreshToken过期时间
        String refreshToken = getRefreshTokenByUserId(userId);
        stringRedisTemplate.expire(REFRESH_TOKEN + refreshToken, REFRESH_TOKEN_TTL, TimeUnit.SECONDS);
        stringRedisTemplate.expire(REFRESH_TOKEN_REVERSE + userId, REFRESH_TOKEN_TTL, TimeUnit.SECONDS);

        //  根据userId和role创建JWT
        return JwtUtils.createToken(userId, role);
    }


    /**
     * 根据refreshToken获取userId<br/>
     *
     * @param refreshToken refreshToken
     * @return userId
     */
    @Override
    public String getUserIdByRefreshToken(String refreshToken) {
        return stringRedisTemplate.opsForValue().get(REFRESH_TOKEN + refreshToken);
    }


    /**
     * 根据userId获取refreshToken<br/>
     *
     * @param userId 用户Id
     * @return refreshToken
     */
    @Override
    public String getRefreshTokenByUserId(String userId) {
        return stringRedisTemplate.opsForValue().get(REFRESH_TOKEN_REVERSE + userId);
    }


    /**
     * 根据userId创建refreshToken
     *
     * @param userId 用户id
     * @return refreshToken字符串(Simple Token)
     */
    @Override
    public String createRefreshToken(String userId) {
        // 删除旧的refreshToken
        deleteRefreshToken(userId);
        // 生成随机的Token值
        String token = SimpleTokenUtils.createTokenValue();
        // 存入Redis中
        stringRedisTemplate.opsForValue().set(REFRESH_TOKEN + token, userId, REFRESH_TOKEN_TTL, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(REFRESH_TOKEN_REVERSE + userId, token, REFRESH_TOKEN_TTL, TimeUnit.SECONDS);
        return token;
    }


    /**
     * 删除refreshToken
     *
     * @param userId userId
     */
    @Override
    public void deleteRefreshToken(String userId) {
        // 获取旧的RefreshToken
        String old = getRefreshTokenByUserId(userId);
        if (StringUtils.isNotBlank(old)) {
            // 删除旧的RefreshToken
            stringRedisTemplate.delete(REFRESH_TOKEN + old);
            // 删除旧的RefreshToken映射
            stringRedisTemplate.delete(REFRESH_TOKEN_REVERSE + userId);
        }
    }


}
