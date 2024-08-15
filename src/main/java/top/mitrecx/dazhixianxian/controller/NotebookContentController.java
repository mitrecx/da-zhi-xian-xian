package top.mitrecx.dazhixianxian.controller;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import top.mitrecx.dazhixianxian.domain.dto.NotebookContentDTO;
import top.mitrecx.dazhixianxian.domain.po.NotebookContent;
import top.mitrecx.dazhixianxian.domain.vo.BasePageRequest;
import top.mitrecx.dazhixianxian.mapper.NotebookContentMapper;

import java.util.List;

/**
 * <p>
 * 笔记内容 前端控制器
 * </p>
 *
 * @author cx
 * @since 2024-08-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notebook-content")
public class NotebookContentController {
    private final NotebookContentMapper notebookContentMapper;

    @PostMapping("/add")
    public boolean add(@RequestBody NotebookContentDTO notebookContentDTO) {
        NotebookContent notebookContent = new NotebookContent();
        BeanUtils.copyProperties(notebookContentDTO, notebookContent);
        return notebookContentMapper.insert(notebookContent) > 0;
    }

    @GetMapping("/list")
    public List<NotebookContent> list() {
        return notebookContentMapper.selectList(null);
    }

    @GetMapping("/page")
    public Page<NotebookContent> page(@RequestBody BasePageRequest request) {
        Page<NotebookContent> page = new Page<>(request.getPageNumber(), request.getPageSize());
        OrderItem orderItem = OrderItem.desc("create_time");
        page.setOrders(Lists.newArrayList(orderItem));
        return notebookContentMapper.selectPage(page, null);
    }
    @PostMapping("/page2")
    public Page<NotebookContent> page2(@RequestBody BasePageRequest request) {
        // 根据创建时间倒序排序
        Page<NotebookContent> page = new Page<>(request.getPageNumber(), request.getPageSize());
        OrderItem orderItem = OrderItem.desc("create_time");
        page.setOrders(Lists.newArrayList(orderItem));
        return notebookContentMapper.selectPage(page, null);
    }
}
