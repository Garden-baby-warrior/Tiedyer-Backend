package com.cnzakii.tiedyer.common.constant;

/**
 * Redis常量
 *
 * @author Zaki
 * @since 2023-09-05
 **/
public interface RedisConstants {


    /**
     * 用户信息前缀
     */
    String USER_INFO = "user:info:";

    /**
     * 用户信息过期时间，一天
     */
    Long USER_INFO_TTL = 1L;

}
