package com.cnzakii.tiedyer.security.wechat;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serial;
import java.util.Collection;

/**
 * 微信小程序登录：身份验证令牌
 *
 * @author Zaki
 * @since 2023-10-26
 **/
public class WeChatAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // js_code
    private final Object principal;


    /**
     * 没经过身份验证时，初始化权限为空，setAuthenticated(false)设置为不可信令牌
     */
    public WeChatAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
    }


    /**
     * 经过身份验证后，将权限放进去，setAuthenticated(true)设置为可信令牌
     */
    public WeChatAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);// 必须使用 super，因为我们重写了
    }

    /**
     * 任何希望创建未经身份验证的<code>WeChatAuthenticationToken</code>都可以安全地使用此工厂方法。
     *
     * @param principal js_code
     * @return isAuthenticated()为false的<code>WeChatAuthenticationToken<code>
     */
    public static WeChatAuthenticationToken unauthenticated(Object principal) {
        return new WeChatAuthenticationToken(principal);
    }

    /**
     * 任何希望创建经身份验证的<code>WeChatAuthenticationToken</code>都可以安全地使用此工厂方法。
     *
     * @param principal js_code
     * @param authorities authorities
     * @return isAuthenticated()为true的<code>WeChatAuthenticationToken<code>
     */
    public static WeChatAuthenticationToken authenticated(Object principal, Collection<? extends GrantedAuthority> authorities) {
        return new WeChatAuthenticationToken(principal,authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

}
