package top.mitrecx.dazhixianxian.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;
import top.mitrecx.dazhixianxian.domain.dto.AuthDTO;
import top.mitrecx.dazhixianxian.domain.po.User;
import top.mitrecx.dazhixianxian.domain.vo.AuthVO;
import top.mitrecx.dazhixianxian.domain.vo.LoginVO;
import top.mitrecx.dazhixianxian.mapper.AuthMapper;
import top.mitrecx.dazhixianxian.mapper.UserMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private String url;

    public MyAuthenticationSuccessHandler(String url, AuthMapper authMapper,
                                          UserMapper userMapper) {
        this.url = url;
        this.authMapper = authMapper;
        this.userMapper = userMapper;
    }

    public MyAuthenticationSuccessHandler(AuthMapper authMapper, UserMapper userMapper) {
        this.authMapper = authMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = response.getWriter();

        LoginVO loginVO = new LoginVO();
//        loginVO.setToken();

        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class);
        queryWrapper.eq(User::getLoginName, authentication.getName());
        User user = userMapper.selectOne(queryWrapper);
        loginVO.setUserId(user.getUserId());
        loginVO.setLoginName(authentication.getName());
        loginVO.setUsername(user.getUsername());

        List<AuthDTO> authList = authMapper.findAuthByLoginName(authentication.getName());
        List<AuthVO> authVOList = AuthVO.convertToAuthVO(authList);
        loginVO.setAuthVOList(authVOList);
        writer.write(ObjectMappers.mustWriteValue(DzResponse.ok(loginVO)));

        // writer.write(ObjectMappers.mustWriteValue(DzResponse.ok(request.getCookies())));
        writer.flush();
    }

}
