package top.mitrecx.dazhixianxian.common;

public enum BizCode {
    OK("0000", "成功"),
    LOGIN_UNAME_PASSWD_ERROR("0001", "用户名或密码错误"),
    LOGIN_UNAUTHENTICATED_ERROR("0002", "您未登录, 请先登录"),
    REQUEST_ARGUMENTS_ERROR("0003", "请求参数错误: %s"),
    ;

    final String code;
    final String message;

    BizCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
