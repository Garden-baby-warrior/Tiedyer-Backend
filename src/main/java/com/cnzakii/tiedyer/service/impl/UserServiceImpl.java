package com.cnzakii.tiedyer.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.User;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.mapper.UserMapper;
import com.cnzakii.tiedyer.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.cnzakii.tiedyer.common.constant.RedisConstants.USER_INFO;
import static com.cnzakii.tiedyer.common.constant.RedisConstants.USER_INFO_TTL;
import static com.cnzakii.tiedyer.common.constant.RoleConstants.ROLE_USER;

/**
 * user服务实现类
 *
 * @author zaki
 * @since 2023-10-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private Snowflake snowflake;


    /**
     * 通过openId创建用户信息
     *
     * @param openId openId
     * @return user对象
     */
    @Override
    public User createUser(String openId) {
        // 生成唯一id
        long id = snowflake.nextId();
        User user = new User();
        user.setId(id);
        user.setOpenId(openId);
        user.setRole(ROLE_USER);

        int i = userMapper.insert(user);
        if (i == 0) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "创建用户信息失败");
        }

        return user;
    }

    /**
     * 根据用户Id获取用户信息
     *
     * @param userId 用户Id
     * @return user对象
     */
    @Override
    public User getUserInfoById(Long userId) {
        String key = USER_INFO + userId;
        User one;

        // 先查询redis，
        one = Optional.ofNullable(stringRedisTemplate.opsForValue().get(key))
                .map(json -> JSONUtil.toBean(json, User.class))
                .orElseGet(() -> {
                    // redis不存在则查询数据库，数据库仍不存在则抛出异常
                    User user = Optional.ofNullable(userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId)))
                            .orElseThrow(() -> new BusinessException(ResponseStatus.FAIL, "用户不存在"));
                    stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(user), USER_INFO_TTL, TimeUnit.DAYS);
                    return user;
                });

        return one;
    }


    /**
     * 通过openId获取用户信息
     *
     * @param openId openId
     * @return user对象
     */
    @Override
    public User getUserInfoByOpenId(String openId) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openId));
    }


    /**
     * 根据userId更新用户昵称和头像
     *
     * @param userId     用户ID
     * @param nickName   昵称
     * @param avatarPath 头像路径
     */
    @Override
    public void updateUserInfo(Long userId, String nickName, String avatarPath) {
        if (StringUtils.isAnyBlank(nickName, avatarPath)) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "昵称或者头像路径为空");
        }

        // 更新user信息
        int i = userMapper.update(null, new LambdaUpdateWrapper<User>()
                .set(User::getNickName, nickName)
                .set(User::getAvatarPath, avatarPath)
                .eq(User::getId, userId)
        );
        if (i == 0) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "更新用户信息失败");
        }

        // 更新Redis缓存
        String key = USER_INFO + userId;
        stringRedisTemplate.delete(key);
        getUserInfoById(userId);
    }


}
