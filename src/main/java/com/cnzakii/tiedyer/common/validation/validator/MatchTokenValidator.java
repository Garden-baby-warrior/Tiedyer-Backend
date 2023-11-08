package com.cnzakii.tiedyer.common.validation.validator;

import com.cnzakii.tiedyer.common.validation.annotation.MatchToken;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;


/**
 * 注解MatchToken 的Validator
 * 校验token解析出的id是否和传入用户id是否一致<br>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-05-14
 **/
public class MatchTokenValidator implements ConstraintValidator<MatchToken, Long> {
    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        // 获取用户Id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String) authentication.getPrincipal());

        return Objects.equals(id, userId);
    }
}
