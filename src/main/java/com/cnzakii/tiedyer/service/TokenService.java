package com.cnzakii.tiedyer.service;

/**
 * token服务
 *
 * @author Zaki
 * @since 2023-10-28
 **/
public interface TokenService {


    /**
     * 根据userId和role创建accessToken
     *
     * @param userId 用户id
     * @param role   用户角色
     * @return accessToken字符串(JWT)
     */
    String createAccessToken(String userId, String role);


    /**
     * 根据refreshToken获取userId<br/>
     *
     * @param refreshToken refreshToken
     * @return userId
     */
    String getUserIdByRefreshToken(String refreshToken);


    /**
     * 根据userId获取refreshToken<br/>
     *
     * @param userId 用户Id
     * @return refreshToken
     */
    String getRefreshTokenByUserId(String userId);


    /**
     * 根据userId创建refreshToken
     *
     * @param userId 用户id
     * @return refreshToken字符串(Simple Token)
     */
    String createRefreshToken(String userId);

    /**
     * 删除refreshToken
     *
     * @param userId userId
     */
    void deleteRefreshToken(String userId);


}
