package top.mitrecx.dazhixianxian.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author cx
 * @since 2024-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_auth")
@ApiModel(value="Auth对象", description="权限表")
public class Auth implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    @TableId(value = "auth_id")
    private String authId;

    @ApiModelProperty(value = "权限名称")
    private String authName;

    @ApiModelProperty(value = "父级权限ID")
    private String parentId;

    @ApiModelProperty(value = "权限URL")
    private String authUrl;

    @ApiModelProperty(value = "请求方式: GET; POST; DELETE; PUT")
    private String method;

    @ApiModelProperty(value = "权限类型: 0 菜单; 1 按钮")
    private Integer authType;

    @ApiModelProperty(value = "层级: 1 一级菜单; 2 二级菜单 等")
    private Integer authLevel;

    @ApiModelProperty(value = "排序")
    private Integer authOrder;

    @ApiModelProperty(value = "是否生效: 0 失效; 1 生效")
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
