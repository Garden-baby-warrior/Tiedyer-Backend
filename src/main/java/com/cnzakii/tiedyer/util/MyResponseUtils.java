package com.cnzakii.tiedyer.util;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * HTTP响应，工具类
 *
 * @author Zaki
 * @since 2023-10-26
 **/
public class MyResponseUtils {

    private final static String RESPONSE_TYPE = "application/json;charset=utf-8";

    public static void success(HttpServletResponse response, Object msg) throws IOException {
        base(response, HttpServletResponse.SC_OK, ResponseResult.success(msg));
    }

    public static void base(HttpServletResponse response, ResponseStatus status, String msg) throws IOException {
        response.setStatus(200);
        response.setContentType(RESPONSE_TYPE);
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(ResponseResult.base(status,msg)));
    }

    public static void base(HttpServletResponse response, int code, Object msg) throws IOException {
        response.setStatus(code);
        response.setContentType(RESPONSE_TYPE);
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(msg));
    }
}
