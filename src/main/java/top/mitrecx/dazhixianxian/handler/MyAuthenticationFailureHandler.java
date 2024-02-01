package top.mitrecx.dazhixianxian.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import top.mitrecx.dazhixianxian.common.BizCode;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;

import java.io.IOException;
import java.io.PrintWriter;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private String url;

    public MyAuthenticationFailureHandler(String url) {
        this.url = url;
    }

    public MyAuthenticationFailureHandler() {
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {
        DzResponse<String> r =
                DzResponse.<String>builder().code(BizCode.LOGIN_UNAME_PASSWD_ERROR.getCode())
                        .message(BizCode.LOGIN_UNAME_PASSWD_ERROR.getMessage())
                        .build();
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        try (PrintWriter out = response.getWriter()) {
            out.print(ObjectMappers.mustWriteValue(r));
            out.flush();
        }
        // response.sendRedirect(url);
    }
}
