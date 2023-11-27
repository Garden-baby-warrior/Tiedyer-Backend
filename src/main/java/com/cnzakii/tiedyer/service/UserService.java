package com.cnzakii.tiedyer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cnzakii.tiedyer.entity.User;
import org.springframework.web.multipart.MultipartFile;

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
     * 根据userId更新用户昵称
     *
     * @param userId     用户ID
     * @param nickName   昵称
     *
     */
    void updateUserInfo(Long userId, String nickName);


    /**
     * 更新用户头像
     *
     * @param userId 用户id
     * @param avatar   头像
     * @return 头像地址
     */
    String updateUserAvatar(Long userId, MultipartFile avatar);


    /**
     * 添加用户积分
     *
     * @param userId      用户id
     * @param points      增加的积分数
     * @param description 描述
     */
    void updatePoints(Long userId, int points,String description);


}
