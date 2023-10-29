package com.cnzakii.tiedyer.security.handler;

import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.util.MyResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.cnzakii.tiedyer.common.http.ResponseStatus.NO_AUTHORITIES;

/**
 * 权限不足的处理程序
 *
 * @author Zaki
 * @since 2023-10-29
 **/
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        BusinessException e = (BusinessException) request.getAttribute("BusinessException");

        if (e != null) {
            resolver.resolveException(request, response, null, e);
        } else {
            MyResponseUtils.base(response, NO_AUTHORITIES, NO_AUTHORITIES.getDescription());
        }
    }
}
