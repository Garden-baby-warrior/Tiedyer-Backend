package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.User;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.model.dto.AuthUserDTO;
import com.cnzakii.tiedyer.security.wechat.WeChatAuthenticationToken;
import com.cnzakii.tiedyer.service.TokenService;
import com.cnzakii.tiedyer.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 用户身份认证接口
 *
 * @author Zaki
 * @since 2023-10-27
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private TokenService tokenService;

    @Resource
    private UserService userService;

    @Resource
    private AuthenticationManager authenticationManager;


    @PostMapping("/wx-login")
    public ResponseResult<AuthUserDTO> userLoginByWeChat(@RequestParam("js_code") String jsCode) {
        // 创建尚未认证的weChatAuthenticationToken
        WeChatAuthenticationToken weChatAuthenticationToken = WeChatAuthenticationToken.unauthenticated(jsCode);

        // 交由WeChatAuthenticationProvider进行认证
        Authentication authentication = authenticationManager.authenticate(weChatAuthenticationToken);

        // 将给定的SecurityContext与当前执行线程相关联
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取验证成功的user对象
        AuthUserDTO authenticationDTO = (AuthUserDTO) authentication.getPrincipal();

        // 构建refreshToken
        String userId = String.valueOf(authenticationDTO.getUserId());
        String refreshToken = tokenService.createRefreshToken(userId);
        authenticationDTO.setRefreshToken(refreshToken);

        // 构建accessToken
        String accessToken = tokenService.createAccessToken(userId, authenticationDTO.getRole());
        authenticationDTO.setAccessToken(accessToken);

        return ResponseResult.success(authenticationDTO);
    }


    /**
     * 根据refreshToken刷新accessToken
     *
     * @param refreshToken refreshToken
     * @return accessToken
     */
    @PutMapping("/token/refresh")
    public ResponseResult<String> refreshAccessToken(@RequestParam String refreshToken) {
        String userId = tokenService.getUserIdByRefreshToken(refreshToken);
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(ResponseStatus.NO_AUTHENTICATION, "refreshToken无效");
        }
        // 获取user信息
        User userInfo = userService.getUserInfoById(Long.valueOf(userId));
        // 构建accessToken
        String accessToken = tokenService.createAccessToken(userId, userInfo.getRole());
        return ResponseResult.success(accessToken);
    }


}
