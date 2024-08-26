package top.mitrecx.dazhixianxian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.domain.dto.UserDTO;
import top.mitrecx.dazhixianxian.domain.po.DzConfig;
import top.mitrecx.dazhixianxian.domain.po.User;
import top.mitrecx.dazhixianxian.domain.po.User;
import top.mitrecx.dazhixianxian.domain.vo.UserVO;
import top.mitrecx.dazhixianxian.domain.vo.PageUserRequest;
import top.mitrecx.dazhixianxian.mapper.UserMapper;
import top.mitrecx.dazhixianxian.service.IDzConfigService;
import top.mitrecx.dazhixianxian.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static top.mitrecx.dazhixianxian.common.BizCode.REQUEST_ARGUMENTS_ERROR;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2024-08-26
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final IDzConfigService dzConfigService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DzResponse<?> addUser(UserDTO user) {
        // 1. 校验参数
        if (user == null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "请求参数不能为空");
        }
        if (StringUtils.isBlank(user.getLoginName())) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "登录名不能为空");
        }
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getLoginName, user.getLoginName());
        User exist = this.getOne(queryWrapper);
        if (exist != null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "用户名已存在");
        }

        // 2. 新增
        User userPo = new User();
        BeanUtils.copyProperties(user, userPo);
        // 写了密码
        if (!StringUtils.isBlank(userPo.getPassword())) {
            userPo.setPassword(passwordEncoder.encode(userPo.getPassword()));
        } else {
            // 未写密码，则从配置表取
            LambdaQueryWrapper<DzConfig> configQueryWrapper = Wrappers.lambdaQuery(DzConfig.class);
            configQueryWrapper.eq(DzConfig::getConfigName, "password");
            DzConfig config = dzConfigService.getOne(configQueryWrapper);
            userPo.setPassword(passwordEncoder.encode(config.getConfigValue()));
        }
        this.save(userPo);
        return DzResponse.ok();
    }

    @Override
    public DzResponse<?> updateUser(UserDTO request) {
        // 1. 校验参数
        if (request == null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "请求参数不能为空");
        }
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getLoginName, request.getLoginName());
        User exist = this.getOne(queryWrapper);
        if (exist == null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "用户不存在");
        }
        // 密码不可修改
        // request.setPassword(passwordEncoder.encode(request.getPassword()));
        // 2. 更新
        User userPo = new User();
        BeanUtils.copyProperties(request, userPo);
        this.updateById(userPo);
        return DzResponse.ok();
    }

    @Override
    public DzResponse<?> deleteUser(long userId) {
        // 1. 校验参数
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUserId, userId);
        User exist = this.getOne(queryWrapper);
        if (exist == null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "用户不存在");
        }
        // 2. 删除
        exist.setDeleted(true);
        this.updateById(exist);
        return DzResponse.ok();
    }

    @Override
    public DzResponse<UserVO> detailsUser(Long userId) {
        // 1. 校验参数
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUserId, userId);
        User exist = this.getOne(queryWrapper);
        if (exist == null) {
            return DzResponse.<UserVO>builder().fail(REQUEST_ARGUMENTS_ERROR, "用户不存在").build();
        }
        // 2. 转换
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(exist, vo);
        return DzResponse.ok(vo);
    }

    @Override
    public DzResponse<Page<UserVO>> pageUser(PageUserRequest request) {
        // 1. 校验参数
        if (request == null) {
            return DzResponse.<Page<UserVO>>builder().fail(REQUEST_ARGUMENTS_ERROR, "请求参数不能为空").build();
        }
        // 2. 分页查询
        Page<User> page = new Page<>(request.getPageNumber(), request.getPageSize());
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class);
        if (StringUtils.isNotBlank(request.getLoginName())) {
            queryWrapper.like(User::getLoginName, request.getLoginName());
        }
        if (StringUtils.isNotBlank(request.getUsername())) {
            queryWrapper.like(User::getUsername, request.getUsername());
        }
        if (StringUtils.isNotBlank(request.getEmail())) {
            queryWrapper.like(User::getEmail, request.getEmail());
        }
        if (StringUtils.isNotBlank(request.getPhone())) {
            queryWrapper.like(User::getPhone, request.getPhone());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            queryWrapper.eq(User::getStatus, request.getStatus());
        }
        if (request.getDeleted() != null) {
            queryWrapper.eq(User::getDeleted, request.getDeleted());
        }

        Page<User> pageData = this.page(page, queryWrapper);
        // 3. 转换
        List<UserVO> list = new ArrayList<>();
        pageData.getRecords().forEach(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            list.add(vo);
        });
        Page<UserVO> tPage = new Page<>(pageData.getCurrent(), pageData.getSize(), pageData.getTotal());
        tPage.setRecords(list);
        return DzResponse.ok(tPage);
    }
}
