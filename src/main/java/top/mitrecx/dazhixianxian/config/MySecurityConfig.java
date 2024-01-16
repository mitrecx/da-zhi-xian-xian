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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.mitrecx.dazhixianxian.handler.MyAccessDeniedHandler;
import top.mitrecx.dazhixianxian.handler.MyAuthenticationFailureHandler;
import top.mitrecx.dazhixianxian.handler.MyAuthenticationSuccessHandler;
import top.mitrecx.dazhixianxian.handler.MyLogoutSuccessHandler;
import top.mitrecx.dazhixianxian.service.JsonAuthenticationEntryPoint;
import top.mitrecx.dazhixianxian.service.JsonAuthenticationFilter;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class MySecurityConfig {
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//    @Bean
//    CorsConfigurationSource corsConfigurationSource2() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


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
                .authorizeHttpRequests(request -> {
                            request.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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
        // 设置login成功返回的JSON数据
        loginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        // 设置login失败返回的JSON数据
        loginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());

        return loginFilter;
    }

}
