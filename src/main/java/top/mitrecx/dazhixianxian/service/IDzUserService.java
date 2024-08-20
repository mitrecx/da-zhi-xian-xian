package top.mitrecx.dazhixianxian.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.domain.dto.DzUserDTO;
import top.mitrecx.dazhixianxian.domain.po.DzUser;
import com.baomidou.mybatisplus.extension.service.IService;
import top.mitrecx.dazhixianxian.domain.vo.BasePageRequest;
import top.mitrecx.dazhixianxian.domain.vo.DzUserVO;
import top.mitrecx.dazhixianxian.domain.vo.PageUserRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cx
 * @since 2024-08-15
 */
public interface IDzUserService extends IService<DzUser> {
    public DzResponse<?> addUser(DzUserDTO user);

    DzResponse<?> updateUser(DzUserDTO request);

    DzResponse<?> deleteUser(long userId);

    DzResponse<DzUserVO> detailsUser(Long userId);

    DzResponse<Page<DzUserVO>> pageUser(PageUserRequest request);
}
