package top.mitrecx.dazhixianxian.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "English2Word对象", description = "自考英语二单词手册")
public class English2WordDTO {
    @ApiModelProperty(value = "wordId")
    private Integer wordId;

    @ApiModelProperty(value = "序号")
    private Integer wordOrder;

    @ApiModelProperty(value = "英文")
    private String word;

    @ApiModelProperty(value = "音标")
    private String phoneticSymbol;

    @ApiModelProperty(value = "中文意思")
    private String chinese;

    @ApiModelProperty(value = "频率")
    private Integer frequency;
}
