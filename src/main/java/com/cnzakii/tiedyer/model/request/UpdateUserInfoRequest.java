package com.cnzakii.tiedyer.model.request;

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
    private Long id;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    /**
     * 用户头像地址
     */
    @NotBlank(message = "头像路径不能为空")
    private String avatarPath;
}
