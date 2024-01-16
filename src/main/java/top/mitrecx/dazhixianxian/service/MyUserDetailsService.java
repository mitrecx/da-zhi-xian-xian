package top.mitrecx.dazhixianxian.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;
import top.mitrecx.dazhixianxian.dal.dao.ext.DzUserExtMapper;
import top.mitrecx.dazhixianxian.dal.entity.DzUser;

@Service("userDetailsService")
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private DzUserExtMapper dzUserExtMapper;

    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("xxxxxx");
        System.out.println(encode);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        DzUser dzUser = dzUserExtMapper.selectByLoginName(username);
        log.info("登录用户名: {}, 用户信息: {}", username, ObjectMappers.mustWriteValue(dzUser));

        // 如果用户不存在, 抛出 UsernameNotFoundException
        if (dzUser == null) {
            throw new UsernameNotFoundException("not found.");
        }

        return new User("admin",
                dzUser.getPassword(), // DB 里存储的就是加密后的密码
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,/main.html"));
    }
}
