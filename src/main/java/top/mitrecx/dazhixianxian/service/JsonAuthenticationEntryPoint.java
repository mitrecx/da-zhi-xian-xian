package top.mitrecx.dazhixianxian.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;
import top.mitrecx.dazhixianxian.vo.DzResponse;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        DzResponse<Object> r = new DzResponse.Builder<>("02").message("您未登录, 请先登录").build();
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        try (PrintWriter out = response.getWriter()) {
            out.print(ObjectMappers.mustWriteValue(r));
            out.flush();
        }
    }
}
