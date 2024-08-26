package top.mitrecx.dazhixianxian.domain.vo;

import lombok.Data;
import top.mitrecx.dazhixianxian.domain.dto.AuthDTO;

import java.util.List;

@Data
public class LoginVO {
    private Integer userId;
    private String LoginName;
    private String username;
    private String token;

    private List<AuthVO> authVOList;
}
