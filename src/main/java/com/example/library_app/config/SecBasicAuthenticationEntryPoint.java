package com.example.library_app.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SecBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    // mục đích sử dụng : để tự tạo lỗi dựa trên kiểu lỗi mong muốn trả về khi user đăng nhập sai username, password
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("WWW-Authenticate", "Basic realm='" + getRealmName() + "'");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.print("{\"code\":\"INVALID_TOKEN\",\"message\":\"Token is invalid\"}");
        writer.flush();
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("Basic Authentication");
        super.afterPropertiesSet();
    }
}
