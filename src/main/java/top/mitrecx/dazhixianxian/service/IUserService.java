package top.mitrecx.dazhixianxian.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.domain.dto.UserDTO;
import top.mitrecx.dazhixianxian.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import top.mitrecx.dazhixianxian.domain.vo.UserVO;
import top.mitrecx.dazhixianxian.domain.vo.PageUserRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cx
 * @since 2024-08-26
 */
public interface IUserService extends IService<User> {
    DzResponse<?> addUser(UserDTO user);

    DzResponse<?> updateUser(UserDTO request);

    DzResponse<?> deleteUser(long userId);

    DzResponse<UserVO> detailsUser(Long userId);

    DzResponse<Page<UserVO>> pageUser(PageUserRequest request);
}
