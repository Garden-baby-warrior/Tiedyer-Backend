package com.cnzakii.tiedyer.security.wechat;

import com.cnzakii.tiedyer.model.dto.AuthUserDTO;
import com.cnzakii.tiedyer.service.AuthService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 微信小程序登录：Provider
 *
 * @author Zaki
 * @since 2023-10-26
 **/
@Slf4j
@Data
public class WeChatAuthenticationProvider implements AuthenticationProvider {


    private AuthService authService;


    /**
     * 对<code>WeChatAuthenticationToken</code>进行认证
     *
     * @param authentication 身份验证请求对象--<code>WeChatAuthenticationToken</code>
     * @return 完成身份验证后的<code>WeChatAuthenticationToken</code>
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        log.info("微信身份验证验证请求: {}", authentication);

        // 获取js_code
        WeChatAuthenticationToken weChatAuthenticationToken = (WeChatAuthenticationToken) authentication;
        String jsCode = (String) weChatAuthenticationToken.getPrincipal();

        // 尝试获取AuthUserDTO对象
        AuthUserDTO authUserDTO = authService.loginByWeChat(jsCode);

        if (authUserDTO == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        WeChatAuthenticationToken result = WeChatAuthenticationToken.authenticated(authUserDTO, authUserDTO.getAuthorities());

        result.setDetails(weChatAuthenticationToken.getDetails());
        return result;
    }

    /**
     * 指定<code>WeChatAuthenticationProvider</code>认证<code>WeChatAuthenticationToken</code>
     *
     * @param authentication WeChatAuthenticationToken
     * @return true or false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return WeChatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
