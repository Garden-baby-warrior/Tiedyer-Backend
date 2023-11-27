package com.cnzakii.tiedyer.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.User;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.mapper.UserMapper;
import com.cnzakii.tiedyer.service.UserPointsHistoryService;
import com.cnzakii.tiedyer.service.UserService;
import com.cnzakii.tiedyer.util.MyFileUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private Snowflake snowflake;

    @Resource
    private UserPointsHistoryService pointsHistoryService;


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
        user.setNickName("tiedyer");
        user.setAvatarPath("");
        user.setPoints(0);

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
     * 根据userId更新用户昵称
     *
     * @param userId   用户ID
     * @param nickName 昵称
     */
    @Override
    public void updateUserInfo(Long userId, String nickName) {
        if (StringUtils.isBlank(nickName)) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "昵称无效");
        }

        // 更新user信息
        int i = userMapper.update(null, new LambdaUpdateWrapper<User>()
                .set(User::getNickName, nickName)
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

    /**
     * 更新用户头像
     *
     * @param userId 用户id
     * @param avatar 头像
     * @return 头像地址
     */
    @Override
    public String updateUserAvatar(Long userId, MultipartFile avatar) {
        // 根据Id获取当前用户
        User user = getUserInfoById(userId);

        String oldIconPath = user.getAvatarPath();
        if (StringUtils.isNotBlank(oldIconPath)) {
            // 删除旧的avatar
            MyFileUtils.deleteFile(oldIconPath);
        }

        // 上传头像
        LocalDate localDate = LocalDate.now();
        String subPath = "/avatar/" + localDate.getYear() + "/" + localDate.getMonthValue() + "/" + localDate.getDayOfMonth() + "/";

        String avatarPath = MyFileUtils.uploadFile(subPath, avatar);

        if (StrUtil.isEmpty(avatarPath)) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "头像保存失败");
        }

        // 更新数据库
        int i = userMapper.update(null, new LambdaUpdateWrapper<User>().eq(User::getId, userId).set(User::getAvatarPath, avatarPath));
        if (i == 0) {
            log.error("更新用户({})头像失败失败", userId);
            throw new BusinessException(ResponseStatus.FAIL, "更新失败");
        }


        // 更新Redis缓存
        String key = USER_INFO + userId;
        stringRedisTemplate.delete(key);
        getUserInfoById(userId);

        // 返回子路径
        return avatarPath;
    }

    /**
     * 更新用户积分
     *
     * @param userId      用户id
     * @param points      增加的积分数
     * @param description 描述
     */
    @Transactional
    @Override
    public void increasePoints(Long userId, int points, String description) {
        // 添加积分
        userMapper.increasePoints(userId, points);
        // 添加日志
        pointsHistoryService.addPointLog(userId, points, description);
        // 更新Redis缓存
        String key = USER_INFO + userId;
        stringRedisTemplate.delete(key);
        getUserInfoById(userId);
    }

    /**
     * 减少用户积分
     *
     * @param userId      用户ID
     * @param points      减少的积分数
     * @param description 描述
     */
    @Override
    public void decreasePoints(Long userId, int points, String description) {
        // 减少积分
        int i = userMapper.decreasePoints(userId, points);
        if (i == 0) {
            throw new BusinessException(ResponseStatus.FAIL, "积分不足");
        }
        // 添加日志
        pointsHistoryService.addPointLog(userId, -points, description);
        // 更新Redis缓存
        String key = USER_INFO + userId;
        stringRedisTemplate.delete(key);
        getUserInfoById(userId);
    }


}
