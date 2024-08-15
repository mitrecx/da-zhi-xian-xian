package top.mitrecx.dazhixianxian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mitrecx.dazhixianxian.common.DzResponse;

@RestController
@RequestMapping("/v1/test")
public class LoginController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world...";
    }

    @GetMapping("/hello2")
    public DzResponse<String> hello2() {
        return DzResponse.ok("hello world2");
    }

}
