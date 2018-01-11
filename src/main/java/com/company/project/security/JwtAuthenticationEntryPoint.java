package com.company.project.security;

import com.alibaba.fastjson.JSON;
import com.company.project.core.ResultCode;
import com.company.project.core.ResultGenerator;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 处理未通过Security认证的请求
 * @author jinhuaquan
 * @date 2017/7/31
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint,Serializable{

    private static final long serialVersionUID = -8970718410437077606L;

    private static final String AUTHENTICATION_FAIL_MESSAGE = "Authentication failed";

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();

        out.write(JSON.toJSONString(ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED, AUTHENTICATION_FAIL_MESSAGE)));
    }
}
