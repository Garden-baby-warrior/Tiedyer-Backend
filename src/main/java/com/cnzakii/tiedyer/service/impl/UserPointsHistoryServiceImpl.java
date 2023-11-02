package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.UserPointsHistory;
import com.cnzakii.tiedyer.mapper.UserPointsHistoryMapper;
import com.cnzakii.tiedyer.service.UserPointsHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户积分获取/消耗记录表 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Service
public class UserPointsHistoryServiceImpl extends ServiceImpl<UserPointsHistoryMapper, UserPointsHistory> implements UserPointsHistoryService {

}
