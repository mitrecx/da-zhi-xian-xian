package top.mitrecx.dazhixianxian.domain.vo;

import lombok.Data;

@Data
public class BasePage {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
}
