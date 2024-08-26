package top.mitrecx.dazhixianxian.domain.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String authId;
    private String authName;
    private String parentId;
    // 权限类型: 0 菜单; 1 按钮
    private Integer authType;
    // 层级: 1 一级菜单; 2 二级菜单 等
    private Integer authLevel;
    private Integer authOrder;
}
