package top.mitrecx.dazhixianxian.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.domain.dto.DzUserDTO;
import top.mitrecx.dazhixianxian.domain.vo.BasePageRequest;
import top.mitrecx.dazhixianxian.domain.vo.DzUserVO;
import top.mitrecx.dazhixianxian.domain.vo.PageUserRequest;
import top.mitrecx.dazhixianxian.service.IDzUserService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cx
 * @since 2024-08-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class DzUserController {
    private final IDzUserService service;

    @PostMapping("/add")
    public DzResponse<?> add(@RequestBody DzUserDTO request) {
        return service.addUser(request);
    }

    @PutMapping("/update")
    public DzResponse<?> update(@RequestBody DzUserDTO request) {
        return service.updateUser(request);
    }

    @DeleteMapping("/delete/{userId}")
    public DzResponse<?> delete(@PathVariable("userId") Long userId) {
        return service.deleteUser(userId);
    }

    @GetMapping("/details/{userId}")
    public DzResponse<DzUserVO> details(@PathVariable("userId") Long userId) {
        return service.detailsUser(userId);
    }

    @PostMapping("/page")
    public DzResponse<Page<DzUserVO>> page(@RequestBody PageUserRequest request) {
        return service.pageUser(request);
    }
}
