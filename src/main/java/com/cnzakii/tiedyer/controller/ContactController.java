package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.model.request.ContactUsRequest;
import com.cnzakii.tiedyer.service.CaptchaService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户留言接口
 *
 * @author Zaki
 * @since 2023-09-25
 **/
@RestController
public class ContactController {

    @Resource
    private CaptchaService captchaService;


    /**
     * 用户网页端，Contact us 页面
     *
     * @return 响应数据
     */
    @PostMapping("/contact/us")
    public ResponseResult<String> ReceiveUserMsg(@Validated @RequestBody ContactUsRequest contactUsRequest) {
        // reCAPTCHA 验证
        boolean b = captchaService.verifyRecaptcha(contactUsRequest.getAuthId());

        if(!b){
            // 返回错误信息
           return ResponseResult.fail("reCAPTCHA验证无效");
        }

        // TODO 将消息异步转发给开发人员

        return ResponseResult.success();
    }
}
