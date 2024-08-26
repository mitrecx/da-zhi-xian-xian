package top.mitrecx.dazhixianxian.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author cx
 * @since 2024-08-15
 */
@Data
@ApiModel(value = "User对象", description = "")
public class UserDTO {
    @ApiModelProperty(value = "用户 id")
    private Long userId;
    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户名/昵称")
    private String username;

    @ApiModelProperty(value = "明文密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话号码")
    private String phone;
}
