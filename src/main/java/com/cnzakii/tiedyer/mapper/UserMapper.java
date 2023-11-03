package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zaki
 * @since 2023-10-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 更新用户积分
     *
     * @param userId 用户id
     * @param points 增加的积分数
     */
    void updatePoints(@Param("userId") Long userId, @Param("points") int points);
}
