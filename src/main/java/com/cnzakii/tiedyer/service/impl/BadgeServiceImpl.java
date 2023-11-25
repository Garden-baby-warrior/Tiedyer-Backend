package com.cnzakii.tiedyer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.entity.Badge;
import com.cnzakii.tiedyer.mapper.BadgeMapper;
import com.cnzakii.tiedyer.service.BadgeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 徽章表 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-25
 */
@Service
public class BadgeServiceImpl extends ServiceImpl<BadgeMapper, Badge> implements BadgeService {

}
