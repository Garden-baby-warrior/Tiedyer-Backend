package com.cnzakii.tiedyer.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户留言请求
 *
 * @author Zaki
 * @since 2023-09-27
 **/
@Data
public class ContactUsRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8204751058386365731L;

    @NotBlank(message = "认证id不能为空")
    private String authId;

    @NotBlank(message = "姓名不能为空")
    private String fullName;

    @Email(message = "邮箱格式错误")
    private String email;

    @Max(value = 300, message = "留言的最大字数为300")
    @NotBlank(message = "留言不能为空")
    private String message;

}
