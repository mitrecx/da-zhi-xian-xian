package top.mitrecx.dazhixianxian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.mitrecx.dazhixianxian.domain.po.English2Word;

/**
 * <p>
 * 自考英语二单词手册 Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2024-07-21
 */
public interface English2WordMapper extends BaseMapper<English2Word> {

    public Integer searchPageNumber(String word);
}
