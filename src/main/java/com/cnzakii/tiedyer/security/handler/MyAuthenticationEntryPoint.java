package com.cnzakii.tiedyer.security.handler;

import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.util.MyResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.cnzakii.tiedyer.common.http.ResponseStatus.NO_AUTHENTICATION;

/**
 * 身份验证没有通过的处理程序
 *
 * @author Zaki
 * @since 2023-10-29
 **/
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        BusinessException e = (BusinessException) request.getAttribute("BusinessException");

        if (e!=null){
            resolver.resolveException(request, response, null, e);
        }else {
            MyResponseUtils.base(response, NO_AUTHENTICATION, NO_AUTHENTICATION.getDescription());
        }

    }
}
