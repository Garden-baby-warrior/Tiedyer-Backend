package com.cnzakii.tiedyer.service;

/**
 * 验证码相关的接口
 *
 * @author Zaki
 * @since 2023-09-27
 **/
public interface CaptchaService {

    /**
     * 验证用户对 reCAPTCHA 质询
     *
     * @param authId reCAPTCHA的用户响应令牌
     * @return true or false
     */
    boolean verifyRecaptcha(String authId);

}
