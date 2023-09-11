package com.cnzakii.tiedyer.model.request;

import com.cnzakii.tiedyer.common.validation.annotation.FixedValues;
import com.cnzakii.tiedyer.common.validation.annotation.MatchToken;
import com.cnzakii.tiedyer.rsa.AbstractEncryptedDataContainer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户身份认证请求
 *
 * @author Zaki
 * @since 2023-09-05
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class UserValidateRequest extends AbstractEncryptedDataContainer implements Serializable {

    @Serial
    private static final long serialVersionUID = -6073414198543571439L;

    @MatchToken
    private Long userId;


    @NotEmpty(message = "密码不能为空")
    private String password;

    /*
     * 0 更新密码
     * 1 更新邮箱
     * 2 删除账号
     */
    @FixedValues(values = {0, 1, 2}, message = "type无效")
    private Integer type;
}
