package com.cnzakii.tiedyer.exception;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
     * 处理请求参数异常<br/>
     * 例如：请求参数缺失，请求体缺失
     *
     * @param e exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(value = {
            MissingServletRequestParameterException.class, // 请求参数缺失
            HttpMessageNotReadableException.class // 请求体缺失
    })
    public ResponseResult<BusinessException> handleRequestException(Exception e, HttpServletRequest request) {
        String errorMsg;

        if (e instanceof MissingServletRequestParameterException parameterException) {
            String parameterName = parameterException.getParameterName();
            errorMsg = "Required request parameter " + parameterName + " is missing";
        } else if(e instanceof HttpMessageNotReadableException){
            errorMsg =  "Required request body is missing";
        }else {
            errorMsg = "Illegal request";
        }


        log.error("[{}] {} --> ResponseCode：{},Exception: {}",
                request.getMethod(), request.getRequestURI(), REQUEST_ERROR.getCode(), e.getLocalizedMessage());

        return ResponseResult.base(REQUEST_ERROR, errorMsg);
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
    public ResponseResult<String> handleParameterVerificationException(Exception e, HttpServletRequest request) {
        List<String> errMsgList = new ArrayList<>();

        if (e instanceof BindException bindException) {
            bindException.getBindingResult().getAllErrors()
                    .forEach(a -> errMsgList.add(a.getDefaultMessage()));
        } else if (e instanceof ConstraintViolationException violationException) {
            violationException.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(errMsgList::add);
        } else {
            errMsgList.add("invalid parameter");
        }

        String errMsg = String.join(",", errMsgList);

        log.error("[{}] {} --> ResponseCode：{},Exception: {}",
                request.getMethod(), request.getRequestURI(), REQUEST_ERROR.getCode(), errMsg);

        return ResponseResult.base(REQUEST_ERROR, errMsg);
    }


    /**
     * 处理安全异常
     *
     * @param e 安全异常
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(value = {
            AuthenticationException.class, // 认证异常
            AccessDeniedException.class    // 权限异常
    })
    public ResponseResult<String> handleSecurityException(Exception e, HttpServletRequest request) {
        ResponseStatus responseStatus;

        if (e instanceof AuthenticationException) {
            responseStatus = NO_AUTHENTICATION;
        } else if (e instanceof AccessDeniedException) {
            responseStatus = NO_AUTHORITIES;
        } else {
            responseStatus = FAIL;
        }

        log.error("[{}] {} --> ResponseCode：{},Exception: {}",
                request.getMethod(), request.getRequestURI(), responseStatus.getCode(), e.getLocalizedMessage());

        return ResponseResult.base(responseStatus, responseStatus.getDescription());
    }


    /**
     * 处理业务异常
     *
     * @param e business exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<BusinessException> handleBusinessException(BusinessException e, HttpServletRequest request) {

        log.error("[{}] {} --> ResponseCode：{},Exception: {}",
                request.getMethod(), request.getRequestURI(), e.getCode(), e.getDescription());

        return ResponseResult.base(e.getCode(), null, e.getDescription());
    }

    /**
     * 处理其他异常
     *
     * @param e exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> handleOtherException(Exception e, HttpServletRequest request) {
        log.error("[{}] {} --> ResponseCode：{},Exception: {}",
                request.getMethod(), request.getRequestURI(), SERVER_ERROR.getCode(), e.getLocalizedMessage());

        return ResponseResult.base(SERVER_ERROR, SERVER_ERROR.getDescription());
    }
}