package com.cnzakii.tiedyer.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static com.cnzakii.tiedyer.common.constant.SystemConstants.DATACENTER_ID;
import static com.cnzakii.tiedyer.common.constant.SystemConstants.WORK_ID;

/**
 * 手动注入Bean
 *
 * @author Zaki
 * @since 2023-09-27
 **/
@Configuration
public class ConfigBean {
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    RSA getRSA(){
        return new RSA();
    }


    @Bean
    public Snowflake getSnowflake() {
        return IdUtil.getSnowflake(WORK_ID, DATACENTER_ID);
    }
}
