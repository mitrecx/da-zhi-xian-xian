package top.mitrecx.dazhixianxian;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;
import top.mitrecx.dazhixianxian.domain.dto.LoginUser;
import top.mitrecx.dazhixianxian.service.RedisUtil;
import top.mitrecx.dazhixianxian.util.JwtUtil;

import java.io.IOException;

import static top.mitrecx.dazhixianxian.service.impl.LoginServiceImpl.LOGIN_NAMESPACE;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        String token = request.getHeader("token");
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // 解析 token
        int userId;
        try {
            userId = JwtUtil.parseToken(token);
        } catch (Exception e) {
            log.error("token 无效", e);
            throw new RuntimeException("token 无效");
        }

        String key = LOGIN_NAMESPACE + userId;
        // 获取 redis 中的用户信息
//        LoginUser loginUser = redisUtil.get(key);
        String str = redisUtil.get(key);
        if (StringUtils.isEmpty(str)) {
            filterChain.doFilter(request, response);
            return;
        }

        LoginUser loginUser = ObjectMappers.mustReadValue(str, LoginUser.class);


        // TODO: mitre 2024/8/29 loginUser.getAuthorities()
        // 存入 SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
