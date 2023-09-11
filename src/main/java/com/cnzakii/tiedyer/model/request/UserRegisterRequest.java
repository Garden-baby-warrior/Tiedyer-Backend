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
 * 用户注册请求
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-04-07
 **/

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisterRequest extends AbstractEncryptedDataContainer implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    @Email(message = "邮箱格式错误")
    private String email;

    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+-=;:'\",.<>/?\\[\\]{}|`~]{8,20}$", message = "密码太弱")
    private String password;

    @Pattern(regexp = "^\\d{6}$", message = "验证码无效")
    private String captcha;
}
