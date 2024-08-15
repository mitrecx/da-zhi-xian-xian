package top.mitrecx.dazhixianxian.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author cx
 * @since 2024-08-15
 */
@Data
@ApiModel(value = "DzUser对象", description = "")
public class DzUserVO {
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户名/昵称")
    private String username;

    //@ApiModelProperty(value = "密码")
    //private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "0 正常; 1 锁定; 2 过期")
    private String status;

    @ApiModelProperty(value = "删除标记")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;


}
