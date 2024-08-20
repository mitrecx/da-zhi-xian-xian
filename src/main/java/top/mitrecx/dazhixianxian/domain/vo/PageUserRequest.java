package top.mitrecx.dazhixianxian.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageUserRequest extends BasePageRequest {
    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户名/昵称")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "0 正常; 1 锁定; 2 过期")
    private String status;

    @ApiModelProperty(value = "删除标记")
    private Boolean deleted;
}
