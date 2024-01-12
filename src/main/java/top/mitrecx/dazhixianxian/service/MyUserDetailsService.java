package top.mitrecx.dazhixianxian.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private static final String dbUsername = "Rosie";
    private static final String dbPassword = new BCryptPasswordEncoder().encode("123");

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        /*
         * todo: username password auth 通过数据库 获取.
         * 如果用户不存在, 抛出 UsernameNotFoundException
         */

        if (StringUtils.equals(username, "admin")) {
            return new User("admin",
                    dbPassword, // DB 里存储的就是加密后的密码
                    AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,/main.html"));
        }

        if (StringUtils.equals(username, dbUsername)) {
            return new User(dbUsername,
                    dbPassword, // DB 里存储的就是加密后的密码
                    AuthorityUtils.commaSeparatedStringToAuthorityList("normal,/main.html"));
        }

        System.out.println("用户名不存在...");
        throw new UsernameNotFoundException("not found.");
    }
}
