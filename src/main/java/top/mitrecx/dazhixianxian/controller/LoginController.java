package top.mitrecx.dazhixianxian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.service.LoginRequest;
import top.mitrecx.dazhixianxian.service.LoginService;

@RestController
@RequestMapping("/v2/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public DzResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }
}
