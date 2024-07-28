package top.mitrecx.dazhixianxian.service;

import top.mitrecx.dazhixianxian.domain.po.English2Word;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 自考英语二单词手册 服务类
 * </p>
 *
 * @author cx
 * @since 2024-07-21
 */
public interface IEnglish2WordService extends IService<English2Word> {
    int searchPageNumber(String word);
}
