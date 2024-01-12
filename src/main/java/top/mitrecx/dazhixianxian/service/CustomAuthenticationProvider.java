package top.mitrecx.dazhixianxian.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private UserDetailsService userDetailsService;

    public CustomAuthenticationProvider() {
    }

    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 在这里执行自定义认证逻辑，比如从数据库中验证用户名和密码
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        // 如果认证成功，创建一个包含用户权限的 Authentication 对象
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
