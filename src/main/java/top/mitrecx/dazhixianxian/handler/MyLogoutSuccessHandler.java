package top.mitrecx.dazhixianxian.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType(ContentType.APPLICATION_JSON.toString());
        try (PrintWriter out = response.getWriter()) {
            out.print(ObjectMappers.mustWriteValue(DzResponse.ok()));
            out.flush();
        }
    }
}
