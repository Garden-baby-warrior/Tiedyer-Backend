package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.UserPointsHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户积分获取/消耗记录表 Mapper 接口
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Mapper
public interface UserPointsHistoryMapper extends BaseMapper<UserPointsHistory> {

}
