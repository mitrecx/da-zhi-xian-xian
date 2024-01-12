package top.mitrecx.dazhixianxian.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String url;


    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }
    public MyAuthenticationSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        log.info(user.getUsername());
        log.info(user.getPassword());
        log.info(user.getAuthorities().toString());

        PrintWriter writer = response.getWriter();
        writer.write("token123");
        writer.flush();
    }

}
