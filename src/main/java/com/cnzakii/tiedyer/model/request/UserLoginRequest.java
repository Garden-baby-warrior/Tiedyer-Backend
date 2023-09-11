package com.cnzakii.tiedyer.model.request;


import com.cnzakii.tiedyer.rsa.AbstractEncryptedDataContainer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author Zaki
 * @since 2023-09-04
 **/

@Data
@EqualsAndHashCode(callSuper = true)
public class UserLoginRequest extends AbstractEncryptedDataContainer implements Serializable {

    @Serial
    private static final long serialVersionUID = -7525696505295089510L;

    @Email(message = "邮箱格式错误")
    private String email;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "验证码唯一标识不能为空")
    private String captchaId;

    @Pattern(regexp = "^\\d{4}$", message = "验证码无效")
    private String captcha;
}
