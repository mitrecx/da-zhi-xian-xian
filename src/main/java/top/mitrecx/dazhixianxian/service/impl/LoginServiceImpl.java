package top.mitrecx.dazhixianxian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.domain.dto.AuthDTO;
import top.mitrecx.dazhixianxian.domain.dto.LoginUser;
import top.mitrecx.dazhixianxian.domain.po.User;
import top.mitrecx.dazhixianxian.domain.vo.AuthVO;
import top.mitrecx.dazhixianxian.domain.vo.LoginVO;
import top.mitrecx.dazhixianxian.mapper.AuthMapper;
import top.mitrecx.dazhixianxian.mapper.UserMapper;
import top.mitrecx.dazhixianxian.service.LoginRequest;
import top.mitrecx.dazhixianxian.service.LoginService;
import top.mitrecx.dazhixianxian.service.RedisUtil;
import top.mitrecx.dazhixianxian.util.JwtUtil;

import java.util.List;

import static top.mitrecx.dazhixianxian.common.BizCode.LOGIN_UNAME_PASSWD_ERROR;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    public static final String LOGIN_NAMESPACE = "login:userId:";
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public DzResponse login(LoginRequest request) {
        // AuthenticationManager 进行用户认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // 如果认证失败，则返回错误信息
        if (authentication == null) {
            return DzResponse.fail(LOGIN_UNAME_PASSWD_ERROR);
        }

        // 如果认证成功, 把完整的用户信息存入 redis, userId 作为 key
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        // 返回 token
        Integer userId = principal.getUser().getUserId();
        String jwtToken = JwtUtil.createToken(userId);


        LoginVO loginVO = new LoginVO();
        loginVO.setToken(jwtToken);

        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class);
        queryWrapper.eq(User::getLoginName, authentication.getName());
        User user = userMapper.selectOne(queryWrapper);
        loginVO.setUserId(user.getUserId());
        loginVO.setLoginName(authentication.getName());
        loginVO.setUsername(user.getUsername());

        List<AuthDTO> authList = authMapper.findAuthByLoginName(authentication.getName());
        List<AuthVO> authVOList = AuthVO.convertToAuthVO(authList);
        loginVO.setAuthVOList(authVOList);

        redisUtil.set(LOGIN_NAMESPACE + userId, principal);
        return DzResponse.ok(loginVO);
    }

    @Override
    public DzResponse logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();

        Integer userId = principal.getUser().getUserId();
        redisUtil.delete(LOGIN_NAMESPACE + userId);
        // SecurityContextHolder.clearContext();
        return DzResponse.ok("退出成功");
    }
}
