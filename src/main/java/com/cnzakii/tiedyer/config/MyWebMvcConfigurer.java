package com.cnzakii.tiedyer.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.cnzakii.tiedyer.common.constant.RoleConstants.ROLE_USER;

/**
 * 自定义 Web MVC 配置类，用于配置拦截器和认证规则。
 *
 * @author Zaki
 * @since 2023-09-04
 **/
@Configuration
@Slf4j
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 添加拦截器配置，定义认证规则。
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 登录校验
                    SaRouter.match("/**", r -> StpUtil.checkLogin());
                    // 权限校验 -- 不同模块校验不同权限
                    SaRouter.match("/user/**", r -> StpUtil.checkPermission(ROLE_USER));
                }))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login/**",
                        "/user/register",
                        "/user/rsa",
                        "/captcha/**");
    }
}
