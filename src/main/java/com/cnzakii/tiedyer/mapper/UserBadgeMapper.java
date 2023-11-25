package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.UserBadge;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户徽章表 - 记录用户兑换的徽章 Mapper 接口
 * </p>
 *
 * @author zaki
 * @since 2023-11-25
 */
@Mapper
public interface UserBadgeMapper extends BaseMapper<UserBadge> {

}
