package top.mitrecx.dazhixianxian.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.mitrecx.dazhixianxian.domain.dto.English2WordDTO;
import top.mitrecx.dazhixianxian.domain.po.English2Word;
import top.mitrecx.dazhixianxian.domain.vo.BasePage;
import top.mitrecx.dazhixianxian.domain.vo.English2WordVO;
import top.mitrecx.dazhixianxian.service.IEnglish2WordService;

/**
 * <p>
 * 自考英语二单词手册 前端控制器
 * </p>
 *
 * @author cx
 * @since 2024-07-21
 */
@Api(tags = "自考英语二单词接口")
@RestController
@RequestMapping("/v1/english2-word")
@RequiredArgsConstructor
public class English2WordController {
    private final IEnglish2WordService english2WordService;

    @ApiOperation("新增或更新(wordId 或者 word)")
    @PostMapping
    public void add(@RequestBody English2WordDTO english2WordDTO) {
        English2Word english2Word = BeanUtil.copyProperties(english2WordDTO, English2Word.class);
        QueryWrapper<English2Word> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("word_id", english2Word.getWordId()).or().eq("word", english2Word.getWord());
        English2Word one = english2WordService.getOne(queryWrapper);

        if (one != null) {
            if (one.getWordId().equals(english2Word.getWordId())) {
                english2WordService.updateById(english2Word);
            } else {
                UpdateWrapper<English2Word> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("word", english2Word.getWord());
                english2WordService.update(english2Word, updateWrapper);
            }
        } else {
            english2WordService.save(english2Word);
        }
    }

    @ApiOperation("查询")
    @GetMapping("{id}")
    public English2WordVO queryById(@PathVariable String id) {
        English2Word word = english2WordService.getById(id);
        return BeanUtil.copyProperties(word, English2WordVO.class);
    }

    @ApiOperation("查询")
    @GetMapping("/page")
    public Page<English2Word> page(@RequestBody BasePage request) {
        Page<English2Word> of = Page.of(request.getPageNumber(), request.getPageSize());
        Page<English2Word> page = english2WordService.page(of);
        return page;
    }

    @ApiOperation("查询")
    @PostMapping("/page2")
    public Page<English2Word> pagePost(@RequestBody BasePage request) {
        Page<English2Word> of = Page.of(request.getPageNumber(), request.getPageSize());
        Page<English2Word> page = english2WordService.page(of);
        return page;
    }


}
