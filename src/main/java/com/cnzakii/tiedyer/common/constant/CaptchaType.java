package com.cnzakii.tiedyer.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * 枚举类，定义不同类型的验证码常量和相关信息。
 *
 * @author Zaki
 * @since 2023-09-05
 **/
@Getter
@AllArgsConstructor
public enum CaptchaType {

    /**
     * 验证图形验证码
     */
    IMAGE_CAPTCHA("captcha:image:", 3L, MINUTES),
    /**
     * 注册邮件验证码
     */
    REGISTER_EMAIL_CAPTCHA("captcha:email:register:", 2L, MINUTES);


    /**
     * 验证码类型的前缀
     */
    final String prefix;

    /**
     * 验证码失效时间
     */
    final Long ttl;

    /**
     * 时间单位
     */
    final TimeUnit timeUnit;
}
