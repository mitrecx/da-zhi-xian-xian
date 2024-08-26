package top.mitrecx.dazhixianxian.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.domain.dto.UserDTO;
import top.mitrecx.dazhixianxian.domain.vo.UserVO;
import top.mitrecx.dazhixianxian.domain.vo.PageUserRequest;
import top.mitrecx.dazhixianxian.service.IUserService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cx
 * @since 2024-08-26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final IUserService service;

    @PostMapping("/add")
    public DzResponse<?> add(@RequestBody UserDTO request) {
        return service.addUser(request);
    }

    @PutMapping("/update")
    public DzResponse<?> update(@RequestBody UserDTO request) {
        return service.updateUser(request);
    }

    @DeleteMapping("/delete/{userId}")
    public DzResponse<?> delete(@PathVariable("userId") Long userId) {
        return service.deleteUser(userId);
    }

    @GetMapping("/details/{userId}")
    public DzResponse<UserVO> details(@PathVariable("userId") Long userId) {
        return service.detailsUser(userId);
    }

    @PostMapping("/page")
    public DzResponse<Page<UserVO>> page(@RequestBody PageUserRequest request) {
        return service.pageUser(request);
    }

}
