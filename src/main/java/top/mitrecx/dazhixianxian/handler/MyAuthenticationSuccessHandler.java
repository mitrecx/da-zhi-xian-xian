package top.mitrecx.dazhixianxian.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;

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
        PrintWriter writer = response.getWriter();
        writer.write(ObjectMappers.mustWriteValue(DzResponse.ok(request.getCookies())));
        writer.flush();
    }

}
