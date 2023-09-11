package com.cnzakii.tiedyer.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.cnzakii.tiedyer.common.constant.SystemConstants.DATACENTER_ID;
import static com.cnzakii.tiedyer.common.constant.SystemConstants.WORK_ID;



/**
 * <p>
 * 创建和维护单例对象工具类
 * <p>
 * 该工具类用于创建和维护不同类型的单例对象，例如RSA和Snowflake等。
 * 如果将来需要创建其他单例对象，可以在此类中添加新的配置方法。
 * <p>
 * 请注意：每个配置方法都应返回一个单例对象。
 * <p>
 * @author Zaki
 * @version 1.0
 * @since 2023-03-18
 **/
@Component
public class MySingletonUtils {

    @Bean
    public RSA getRsa() {
        return new RSA();
    }

    @Bean
    public Snowflake getSnowflake() {
        return IdUtil.getSnowflake(WORK_ID, DATACENTER_ID);
    }

}