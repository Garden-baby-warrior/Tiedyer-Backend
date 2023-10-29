package com.cnzakii.tiedyer.security.handler;

import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.service.TokenService;
import com.cnzakii.tiedyer.util.MyResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;

import static com.cnzakii.tiedyer.common.http.ResponseStatus.NO_AUTHENTICATION;

/**
 * 用户登出成功的处理程序
 *
 * @author Zaki
 * @since 2023-10-27
 **/
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    TokenService tokenService;

    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 处理没有权限的异常
        if (authentication == null) {
            BusinessException e = (BusinessException) request.getAttribute("BusinessException");
            if (e == null) {
                e = new BusinessException(NO_AUTHENTICATION);
            }
            resolver.resolveException(request, response, null, e);
            return;
        }

        // 从请求中读取RefreshToken
        String refreshToken = request.getParameter("refreshToken");

        // 根据userId获取RefreshToken
        String userId = (String) authentication.getPrincipal();
        String result = tokenService.getRefreshTokenByUserId(userId);

        // 对比两者
        if (!Objects.equals(result, refreshToken)) {
            BusinessException e = new BusinessException(NO_AUTHENTICATION, "refreshToken无效");
            resolver.resolveException(request, response, null, e);
            return;
        }

        // 删除refreshToken
        tokenService.deleteRefreshToken(userId);

        MyResponseUtils.success(response, null);
    }
}
