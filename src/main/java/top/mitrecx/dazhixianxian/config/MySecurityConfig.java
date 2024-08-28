package top.mitrecx.dazhixianxian.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.mitrecx.dazhixianxian.handler.MyAccessDeniedHandler;
import top.mitrecx.dazhixianxian.handler.MyAuthenticationFailureHandler;
import top.mitrecx.dazhixianxian.handler.MyAuthenticationSuccessHandler;
import top.mitrecx.dazhixianxian.handler.MyLogoutSuccessHandler;
import top.mitrecx.dazhixianxian.mapper.AuthMapper;
import top.mitrecx.dazhixianxian.mapper.UserMapper;
import top.mitrecx.dazhixianxian.service.JsonAuthenticationEntryPoint;
import top.mitrecx.dazhixianxian.service.JsonAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class MySecurityConfig {
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private UserMapper userMapper;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(Arrays.asList("*"));

        // the CORS spec does not allow "*" when allowCredentials is set to true
        configuration.setAllowedOriginPatterns(Arrays.asList("https://mitrecx.top",
                "http://mitrecx.top",
                "http://localhost*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

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
                .cors(Customizer.withDefaults())
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeHttpRequests(request -> {
                            request.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                    .requestMatchers("/v1/about").permitAll()
                                    .requestMatchers("/v2/login").anonymous()
                                    .requestMatchers("/doc/**").permitAll()
                                    .requestMatchers("/doc").permitAll()
//                                    .requestMatchers("/v1/english2-word/**").permitAll()
//                                    .requestMatchers("/v1/notebook-content/**").permitAll()
//                                    .requestMatchers("/v1/user/**").permitAll()
                                    .requestMatchers("/v1/**").authenticated()
                                    .anyRequest().permitAll();
                        }
                )

//                .formLogin(form -> form.loginProcessingUrl("/v1/login").permitAll()
//                        .successHandler(new MyAuthenticationSuccessHandler())
//                        .failureHandler(new MyAuthenticationFailureHandler())
//                )
                .addFilterAt(loginFilter(http), UsernamePasswordAuthenticationFilter.class)

                .logout(logout -> logout.logoutUrl("/v1/logout")
                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                )

                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(jsonAuthenticationEntryPoint)
//                .httpBasic(withDefaults())
        ;
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService).and().build();
    }

    @Bean
    public JsonAuthenticationFilter loginFilter(HttpSecurity http) throws Exception {
        JsonAuthenticationFilter loginFilter = new JsonAuthenticationFilter(authenticationManager(http));
        // 自定义接收的url，默认是login
        loginFilter.setFilterProcessesUrl("/v1/login");
        // 解决 json 参数登录时 sessionId 不变的问题
        loginFilter.setSessionAuthenticationStrategy(new ChangeSessionIdAuthenticationStrategy());
        // 设置login成功返回的JSON数据
        loginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler(authMapper, userMapper));
        // 设置login失败返回的JSON数据
        loginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        // 解决 登录成功后, session 无效问题(其他接口还是要求认证问题)
        // https://www.modb.pro/db/629856
        loginFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        return loginFilter;
    }

}
