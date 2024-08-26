package top.mitrecx.dazhixianxian.domain.vo;

import lombok.Data;
import top.mitrecx.dazhixianxian.domain.dto.AuthDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AuthVO {
    private String authId;
    private String authName;
    private String parentId;
    // 权限类型: 0 菜单; 1 按钮
    private Integer authType;
    // 层级: 1 一级菜单; 2 二级菜单 等
    private Integer authLevel;
    private Integer authOrder;

    private List<AuthVO> children;


    // 将 AuthDTO 列表转换为 AuthVO 列表，并建立树形结构
    public static List<AuthVO> convertToAuthVO(List<AuthDTO> dtoList) {
        Map<String, AuthVO> authMap = new HashMap<>();
        List<AuthVO> rootAuthList = new ArrayList<>();

        // 先将 AuthDTO 转换为 AuthVO，并存入 map 中
        for (AuthDTO dto : dtoList) {
            AuthVO authVO = new AuthVO();
            authVO.setAuthId(dto.getAuthId());
            authVO.setAuthName(dto.getAuthName());
            authVO.setParentId(dto.getParentId());
            authVO.setAuthType(dto.getAuthType());
            authVO.setAuthLevel(dto.getAuthLevel());
            authVO.setAuthOrder(dto.getAuthOrder());
            authVO.setChildren(new ArrayList<>());

            authMap.put(authVO.getAuthId(), authVO);
        }

        // 组装层级结构
        for (AuthVO authVO : authMap.values()) {
            if (authVO.getParentId() == null || authVO.getParentId().isEmpty()
                    || "0".equals(authVO.getParentId())) {
                // 如果 parentId 为 null 或空字符串，则说明是根节点
                rootAuthList.add(authVO);
            } else {
                // 否则，找到父节点，将当前节点添加到父节点的 children 列表中
                AuthVO parentAuth = authMap.get(authVO.getParentId());
                if (parentAuth != null) {
                    parentAuth.getChildren().add(authVO);
                }
            }
        }

        // 根据 authOrder 对 children 列表进行排序
        for (AuthVO authVO : authMap.values()) {
            authVO.getChildren().sort((a, b) -> a.getAuthOrder().compareTo(b.getAuthOrder()));
        }

        // 根节点的排序
        rootAuthList.sort((a, b) -> a.getAuthOrder().compareTo(b.getAuthOrder()));

        return rootAuthList;
    }
}
