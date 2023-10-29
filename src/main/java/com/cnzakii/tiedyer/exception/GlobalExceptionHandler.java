package com.cnzakii.tiedyer.exception;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cnzakii.tiedyer.common.http.ResponseStatus.*;

/**
 * <p>
 * Global exception handler
 * <p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-03-17
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @InitBinder
    public void handleInitBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

    /**
     * 处理参数验证异常
     *
     * @param e exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(value = {
            BindException.class,
            ValidationException.class
    })
    public ResponseResult<String> handleParameterVerificationException(Exception e) {
        List<String> exceptionMsg = new ArrayList<>();
        log.error("ResponseCode：400,Exception: {}", e.getMessage());

        if (e instanceof BindException bindException) {
            bindException.getBindingResult().getAllErrors()
                    .forEach(a -> exceptionMsg.add(a.getDefaultMessage()));
        } else if (e instanceof ConstraintViolationException violationException) {
            violationException.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(exceptionMsg::add);
        } else {
            exceptionMsg.add("invalid parameter");
        }

        return ResponseResult.base(REQUEST_ERROR, String.join(",", exceptionMsg));
    }


    /**
     * 处理业务异常
     *
     * @param businessException business exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<BusinessException> processBusinessException(BusinessException businessException) {
        log.error("ResponseCode：{},Exception: {}", businessException.getCode(), businessException.getDescription());
        return ResponseResult.base(businessException.getCode(), null, businessException.getDescription());
    }

    /**
     * 处理认证异常
     *
     * @param authenticationException authenticationException
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult<String> processNotLoginException(AuthenticationException authenticationException) {
        log.error("ResponseCode：{},Exception: {}", NO_AUTHENTICATION.getCode(), authenticationException.getLocalizedMessage());
        return ResponseResult.base(NO_AUTHENTICATION, NO_AUTHENTICATION.getDescription());
    }

    /**
     * 处理权限异常
     *
     * @param accessDeniedException accessDeniedException
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult<String> processNotPermissionException(AccessDeniedException accessDeniedException) {
        log.error("ResponseCode：{},Exception: {}", NO_AUTHORITIES.getCode(), accessDeniedException.getLocalizedMessage());
        return ResponseResult.base(NO_AUTHORITIES, NO_AUTHORITIES.getDescription());
    }


    /**
     * 处理其他异常
     *
     * @param exception exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult<Exception> processException(Exception exception) {

        log.error("ResponseCode：500,Exception: {}", exception.getLocalizedMessage());

        return ResponseResult.base(SERVER_ERROR, SERVER_ERROR.getDescription());
    }
}