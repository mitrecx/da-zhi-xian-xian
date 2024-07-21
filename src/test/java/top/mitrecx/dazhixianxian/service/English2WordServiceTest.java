package top.mitrecx.dazhixianxian.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;
import top.mitrecx.dazhixianxian.domain.po.English2Word;

@SpringBootTest
class English2WordServiceTest {
    @Autowired
    IEnglish2WordService english2WordService;

    @Test
    void getWords() {
        English2Word word = english2WordService.getById(1);
        System.out.println(word);
    }

    @Test
    void testPageQuery() {
        Page<English2Word> of = Page.of(1, 3);
        of.addOrder(new OrderItem("word_id", true));
        Page<English2Word> page = english2WordService.page(of);

        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
        System.out.println(ObjectMappers.mustWriteValue(page));

    }
}