package com.cnzakii.tiedyer.service.impl;

import com.cnzakii.tiedyer.entity.User;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.model.dto.user.AuthUserDTO;
import com.cnzakii.tiedyer.service.AuthService;
import com.cnzakii.tiedyer.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

import static com.cnzakii.tiedyer.common.http.ResponseStatus.NO_AUTHENTICATION;
import static com.cnzakii.tiedyer.common.http.ResponseStatus.SERVER_ERROR;


/**
 * 认证接口实现类
 *
 * @author Zaki
 * @since 2023-10-29
 **/
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserService userService;


    @Resource
    private RestTemplate restTemplate;

    // 小程序 appId
    @Value("${api.wx.appId}")
    private String appId;

    // 小程序 appSecret
    @Value("${api.wx.secret}")
    private String secret;

    // 授权类型，此处只需填写 authorization_code
    @Value("${api.wx.grantType}")
    private String grantType;


    // 请求地址
    private static final String WX_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @SneakyThrows
    @Override
    public AuthUserDTO loginByWeChat(String jsCode) {
        // 设置请求路径参数
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", this.appId);
        queryParams.add("secret", this.secret);
        queryParams.add("js_code", jsCode);
        queryParams.add("grant_type", grantType);

        // 拼接路径
        URI uri = UriComponentsBuilder.fromHttpUrl(WX_URL)
                .queryParams(queryParams)
                .build()
                .toUri();


        // 发送请求
        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
        log.info(result.getBody());

        // 解析响应内容
        String response = result.getBody();
        JsonNode jsonNode = new ObjectMapper().readTree(response);
        // 尝试获取openId
        JsonNode node = jsonNode.get("openid");
        if (node == null) {
            log.error("小程序登录失败: {}", response);
            // 获取对应状态码
            String errCode = jsonNode.get("errcode").asText();

            if (Objects.equals(errCode, "40029")) {
                // js_code 无效
                throw new BusinessException(NO_AUTHENTICATION, "js_code无效");
            } else if (Objects.equals(errCode, "40226")) {
                // 账号高危
                throw new BusinessException(NO_AUTHENTICATION, "账号高危");
            } else {
                throw new BusinessException(SERVER_ERROR);
            }
        }

        String openId = node.asText();


        // 尝试从数据库中获取user信息
        User user = userService.getUserInfoByOpenId(openId);
        AuthUserDTO authenticationDTO = new AuthUserDTO();

        // 如果user不存在，则创建新的user对象
        if (user == null) {
            user = userService.createUser(openId);
            authenticationDTO.setNewUser(true);
        } else {
            authenticationDTO.setNewUser(false);
        }

        // 设用户id和角色信息
        authenticationDTO.setUserId(user.getId());
        authenticationDTO.setRole(user.getRole());


        return authenticationDTO;
    }
}
