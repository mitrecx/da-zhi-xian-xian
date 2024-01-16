package top.mitrecx.dazhixianxian.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/test")
public class LoginController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world...";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "hello world2...";
    }

}
