package com.cnzakii.tiedyer.security.filter;

import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.security.wechat.WeChatAuthenticationToken;
import com.cnzakii.tiedyer.util.token.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.cnzakii.tiedyer.common.constant.TokenConstants.TOKEN_NAME;
import static com.cnzakii.tiedyer.common.http.ResponseStatus.NO_AUTHENTICATION;


/**
 * 令牌验证过滤器
 *
 * @author Zaki
 * @since 2023-10-25
 **/

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取token
        String accessToken = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(accessToken)) {
            request.setAttribute("BusinessException", new BusinessException(NO_AUTHENTICATION, "找不到Token"));
            doFilter(request, response, filterChain);
            return;
        }

        // 验证token的有效性, 并获取userId，role
        String userId;
        String role;
        try {
            userId = JwtUtils.getUserId(accessToken);
            role = JwtUtils.getAuthentication(accessToken);
        } catch (ExpiredJwtException e) {
            request.setAttribute("BusinessException", new BusinessException(NO_AUTHENTICATION, "Token过期"));
            doFilter(request, response, filterChain);
            return;
        } catch (SignatureException s) {
            request.setAttribute("BusinessException", new BusinessException(NO_AUTHENTICATION, "Token非法"));
            doFilter(request, response, filterChain);
            return;
        } catch (JwtException e) {
            log.error(e.getMessage());
            request.setAttribute("BusinessException", new BusinessException(NO_AUTHENTICATION, "Token无效"));
            doFilter(request, response, filterChain);
            return;
        }

        // 如果token有效,则解析对应权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role);
        authorities.add(auth);


        // 封装一个WeChatAuthenticationToken对象
        WeChatAuthenticationToken weChatAuthenticationToken = WeChatAuthenticationToken.authenticated(userId, authorities);
        // 从HttpServletRequest对象构建详细信息对象，并设置详情
        weChatAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 将给定的SecurityContext与当前执行线程相关联
        SecurityContextHolder.getContext().setAuthentication(weChatAuthenticationToken);

        // 将请求转发给过滤器链下一个filter
        filterChain.doFilter(request, response);
    }
}
