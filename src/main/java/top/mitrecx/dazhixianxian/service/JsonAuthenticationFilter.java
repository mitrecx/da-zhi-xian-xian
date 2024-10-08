package top.mitrecx.dazhixianxian.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;

import java.io.IOException;

/**
 * 处理身份验证表单提交 的过滤器
 */
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JsonAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        if (request.getHeader("Content-Type").equals(MediaType.APPLICATION_JSON.toString())) {
            try {
                LoginRequest loginRequest = ObjectMappers.get().readValue(request.getInputStream(), LoginRequest.class);

                UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                        loginRequest.getUsername(),
                        loginRequest.getPassword());
                setDetails(request, authRequest);
                return getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.attemptAuthentication(request, response);
    }

}
