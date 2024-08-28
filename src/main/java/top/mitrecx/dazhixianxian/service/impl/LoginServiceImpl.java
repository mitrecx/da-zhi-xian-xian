package top.mitrecx.dazhixianxian.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.service.LoginRequest;
import top.mitrecx.dazhixianxian.service.LoginService;

import static top.mitrecx.dazhixianxian.common.BizCode.LOGIN_UNAME_PASSWD_ERROR;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public DzResponse login(LoginRequest request) {
        // AuthenticationManager 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // 如果认证失败，则返回错误信息
        if (authenticate == null) {
            return DzResponse.fail(LOGIN_UNAME_PASSWD_ERROR);
        }

        // 如果认证成功, 把完整的用户信息存入 redis, userId 作为 key
        User principal = (User) authenticate.getPrincipal();
        log.info("用户: {} 登录成功", principal);
        // TODO: 修改 MyUserDetailsServiceImpl 中 loadUserByUsername 方法, 返回完整的 User 对象, 自定义一个 LoginUser
        // TODO: 存入 redis
        // 返回 token


        return DzResponse.ok();
    }
}
