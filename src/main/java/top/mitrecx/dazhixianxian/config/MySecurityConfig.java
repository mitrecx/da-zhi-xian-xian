package top.mitrecx.dazhixianxian.config;


import top.mitrecx.dazhixianxian.handler.MyAuthenticationFailureHandler;
import top.mitrecx.dazhixianxian.handler.MyAuthenticationSuccessHandler;
import top.mitrecx.dazhixianxian.handler.MyLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class MySecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用 CSRF 保护，因为我们是通过 API 进行认证
                .formLogin(form -> form.loginProcessingUrl("/v1/login").permitAll()
                        .successHandler(new MyAuthenticationSuccessHandler())
                        .failureHandler(new MyAuthenticationFailureHandler())
                )
                .logout(logout -> logout.logoutUrl("/v1/logout")
                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                )
                .authorizeHttpRequests((authorize) -> {
                            authorize.anyRequest().authenticated();
                        }
                )
//                .httpBasic(withDefaults())
        ;
        return http.build();
    }
}
