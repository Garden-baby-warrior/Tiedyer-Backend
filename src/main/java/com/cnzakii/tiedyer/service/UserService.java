package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.User;

/**
 * user服务接口
 *
 * @author zaki
 * @since 2023-10-26
 */
public interface UserService extends IService<User> {


    /**
     * 通过openId创建用户信息
     *
     * @param openId openId
     * @return user对象
     */
    User createUser(String openId);

    /**
     * 根据用户Id获取用户信息
     *
     * @param userId 用户Id
     * @return user对象
     */
    User getUserInfoById(Long userId);


    /**
     * 通过openId获取用户信息
     *
     * @param openId openId
     * @return user对象
     */
    User getUserInfoByOpenId(String openId);

    /**
     * 根据userId更新用户昵称和头像
     *
     * @param userId     用户ID
     * @param nickName   昵称
     * @param avatarPath 头像路径
     */
    void updateUserInfo(Long userId, String nickName, String avatarPath);


    /**
     * 添加用户积分
     *
     * @param userId      用户id
     * @param points      增加的积分数
     * @param description 描述
     */
    void updatePoints(Long userId, int points,String description);
}
