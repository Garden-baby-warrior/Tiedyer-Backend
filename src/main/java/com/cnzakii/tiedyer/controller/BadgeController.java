package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.model.dto.badge.BadgeResult;
import com.cnzakii.tiedyer.service.UserBadgeService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 徽章接口
 *
 * @author zaki
 * @since 2023-11-25
 */
@RestController
@RequestMapping("/badge")
public class BadgeController {


    @Resource
    private UserBadgeService userBadgeService;


    /**
     * 用户兑换徽章
     *
     * @param badgeId 徽章ID
     * @return 兑换结果
     */
    @PostMapping("/redeem/{badgeId}")
    public ResponseResult<String> RedeemBadge(@PathVariable("badgeId") Long badgeId) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        // 兑换徽章
        userBadgeService.redeemBadge(userId,badgeId);

        return ResponseResult.success("兑换成功");
    }


    /**
     * 获取用户的徽章兑换情况
     *
     * @return 徽章列表
     */
    @GetMapping("/list")
    public ResponseResult<BadgeResult> getAllBadgeList() {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        BadgeResult badgeResult = userBadgeService.getBadgeResult(userId);
        return ResponseResult.success(badgeResult);
    }


}
