package top.mitrecx.dazhixianxian.service;

import top.mitrecx.dazhixianxian.common.DzResponse;

public interface LoginService {
    DzResponse login(LoginRequest request);

    DzResponse logout();
}
