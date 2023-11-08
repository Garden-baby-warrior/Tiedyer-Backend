package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.entity.User;
import com.cnzakii.tiedyer.model.dto.user.UserDTO;
import com.cnzakii.tiedyer.model.request.user.UpdateUserInfoRequest;
import com.cnzakii.tiedyer.service.UserService;
import com.cnzakii.tiedyer.util.MyBeanUtils;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 *
 * @author zaki
 * @since 2023-10-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 获取用户信息
     *
     * @return userDTO
     */
    @GetMapping("/info")
    public ResponseResult<UserDTO> getUserInfo() {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        // 获取用户信息
        User user = userService.getUserInfoById(userId);
        // 转换成userDTO对象
        UserDTO userDTO = MyBeanUtils.copyProperties(user, UserDTO.class);
        return ResponseResult.success(userDTO);
    }


    /**
     * 更新用户信息
     *
     * @param request 用户信息更新请求体
     * @return success
     */
    @PutMapping("/info")
    public ResponseResult<String> updateUserInfo(@Validated @RequestBody UpdateUserInfoRequest request) {
        Long userId = request.getUserId();
        String nickName = request.getNickName();
        String avatarPath = request.getAvatarPath();
        // 更新用户信息
        userService.updateUserInfo(userId, nickName, avatarPath);

        return ResponseResult.success();
    }


}
