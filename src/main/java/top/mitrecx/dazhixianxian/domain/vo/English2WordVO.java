package top.mitrecx.dazhixianxian.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 自考英语二单词手册
 * </p>
 *
 * @author cx
 * @since 2024-07-21
 */
@Data
@ApiModel(value="English2Word对象", description="自考英语二单词手册")
public class English2WordVO {
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

    @ApiModelProperty(value = "牛津词典")
    private String oxford;

    @ApiModelProperty(value = "是否已经导入牛津词典")
    private Boolean oxfordPopulate;
}
