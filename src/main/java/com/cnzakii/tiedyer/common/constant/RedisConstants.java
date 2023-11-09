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

    /**
     * 用户购物车前缀
     */
    String USER_SHOPPING_CART = "shopping:cart:";


    /**
     * 已经完成每日答题的用户列表
     */
    String DAILY_ANSWERED_USERS_LIST = "game:answered:daily:list";

    /**
     * 用户每日答题列表
     */
    String DAILY_QUESTION_GAME_LIST = "game:question:daily:list:";

}
