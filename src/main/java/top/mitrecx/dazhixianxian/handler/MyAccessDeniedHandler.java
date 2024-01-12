package top.mitrecx.dazhixianxian.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
        PrintWriter writer = response.getWriter();
        Map<String, String> map = new HashMap<>();
        map.put("status", "error");
        map.put("msg", "对不起, 您没有访问权限...");
        String body = new ObjectMapper().writeValueAsString(map);

        writer.write(body);
        writer.flush();
        writer.close();
    }

}
