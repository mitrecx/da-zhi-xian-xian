package top.mitrecx.dazhixianxian.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.mitrecx.dazhixianxian.domain.po.English2Word;
import top.mitrecx.dazhixianxian.mapper.English2WordMapper;
import top.mitrecx.dazhixianxian.service.IEnglish2WordService;

/**
 * <p>
 * 自考英语二单词手册 服务实现类
 * </p>
 *
 * @author cx
 * @since 2024-07-21
 */
@Service
public class English2WordServiceImpl extends ServiceImpl<English2WordMapper, English2Word> implements IEnglish2WordService {

    public int searchPageNumber(String word) {
        if (StringUtils.isEmpty(word)) {
            return 0;
        }
        Integer r = baseMapper.searchPageNumber(word);
        if (r == null) {
            return 0;
        }
        return r;
    }
}
