package top.mitrecx.dazhixianxian.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 自考英语二单词手册
 * </p>
 *
 * @author cx
 * @since 2024-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_english2_word")
@ApiModel(value="English2Word对象", description="自考英语二单词手册")
public class English2Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "word_id", type = IdType.AUTO)
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
