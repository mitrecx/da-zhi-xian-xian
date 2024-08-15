package top.mitrecx.dazhixianxian.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * <p>
 * 笔记内容
 * </p>
 *
 * @author cx
 * @since 2024-08-14
 */
@Data
@ApiModel(value = "NotebookContent对象", description = "笔记内容")
public class NotebookContentDTO {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "笔记ID")
    private Integer noteId;

    @ApiModelProperty(value = "内容")
    private String content;

}
