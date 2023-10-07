package com.cnzakii.tiedyer.service.impl;

import com.cnzakii.tiedyer.service.CaptchaService;
import com.cnzakii.tiedyer.util.MyJsonUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 验证码相关的接口的实现类
 *
 * @author Zaki
 * @since 2023-09-27
 **/
@Service
@Component
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${other.recaptcha.secret}")
    private String recaptchaSecret;

    @Resource
    private RestTemplate restTemplate;


    @Override
    public boolean verifyRecaptcha(String authId) {
        String url = "https://www.google.com/recaptcha/api/siteverify";

        // 设置提交参数
        Map<String, String> map = Map.of(
                "secret", recaptchaSecret,
                "response", authId
        );
        String json = restTemplate.postForObject(url, map, String.class);

        return MyJsonUtils.parseObjectByKey(json, "success", Boolean.class);
    }
}
