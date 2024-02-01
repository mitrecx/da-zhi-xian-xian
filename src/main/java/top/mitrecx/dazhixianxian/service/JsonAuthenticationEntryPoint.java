package top.mitrecx.dazhixianxian.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;

import java.io.IOException;
import java.io.PrintWriter;

import static top.mitrecx.dazhixianxian.common.BizCode.LOGIN_UNAUTHENTICATED_ERROR;

@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        response.setContentType(ContentType.APPLICATION_JSON.toString());
        try (PrintWriter out = response.getWriter()) {
            out.print(ObjectMappers.mustWriteValue(DzResponse.builder().fail(LOGIN_UNAUTHENTICATED_ERROR).build()));
            out.flush();
        }
    }
}
