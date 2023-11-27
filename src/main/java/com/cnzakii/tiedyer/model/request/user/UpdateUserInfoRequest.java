package com.cnzakii.tiedyer.model.request.user;

import com.cnzakii.tiedyer.common.validation.annotation.MatchToken;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新用户信息请求
 *
 * @author Zaki
 * @since 2023-10-26
 **/
@Data
public class UpdateUserInfoRequest implements Serializable {

    /**
     * 用户id
     */
    @MatchToken
    private Long userId;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;

}
