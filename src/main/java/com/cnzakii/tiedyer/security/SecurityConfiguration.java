package com.cnzakii.tiedyer.security;

import com.cnzakii.tiedyer.security.filter.TokenAuthenticationFilter;
import com.cnzakii.tiedyer.security.handler.MyAccessDeniedHandler;
import com.cnzakii.tiedyer.security.handler.MyAuthenticationEntryPoint;
import com.cnzakii.tiedyer.security.handler.MyLogoutSuccessHandler;
import com.cnzakii.tiedyer.security.wechat.WeChatAuthenticationProvider;
import com.cnzakii.tiedyer.service.AuthService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.cnzakii.tiedyer.common.constant.RoleConstants.ROLE_USER;

/**
 * Spring Security 配置
 *
 * @author Zaki
 * @since 2023-10-25
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    @Resource
    private MyLogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private AuthService authService;


    @Resource
    private MyAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private MyAccessDeniedHandler accessDeniedHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and() //支持跨域
                .csrf(AbstractHttpConfigurer::disable) //csrf关闭
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//不使用session


        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // 放行认证接口
                        .requestMatchers("/user/**").hasAnyAuthority(ROLE_USER) // user接口需要user权限
                        .anyRequest().authenticated()
                )
                .formLogin().disable() // 禁用默认表单登录
                .addFilterBefore(tokenAuthenticationFilter(), LogoutFilter.class) // 使用Token校验过滤器
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST")) // 仅允许 POST 请求
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .exceptionHandling()// 允许自定义异常处理
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        return http.build();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }


    @Bean
    public WeChatAuthenticationProvider weChatAuthenticationProvider() {
        WeChatAuthenticationProvider weChatAuthenticationProvider = new WeChatAuthenticationProvider();
        weChatAuthenticationProvider.setAuthService(authService);
        return weChatAuthenticationProvider;
    }


    /**
     * 获取AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
