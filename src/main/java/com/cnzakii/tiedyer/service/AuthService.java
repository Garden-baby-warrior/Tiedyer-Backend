package com.cnzakii.tiedyer.service;

import com.cnzakii.tiedyer.model.dto.AuthUserDTO;

/**
 * 认证接口
 *
 * @author Zaki
 * @since 2023-10-29
 **/
public interface AuthService {

    /**
     * 微信小程序登录
     * @param jsCode 登录时获取的 code，可通过wx.login获取
     * @return  获取对应的User对象
     */
    AuthUserDTO loginByWeChat(String jsCode);
}
