package top.mitrecx.dazhixianxian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.mitrecx.dazhixianxian.common.DzResponse;
import top.mitrecx.dazhixianxian.domain.dto.DzUserDTO;
import top.mitrecx.dazhixianxian.domain.po.DzConfig;
import top.mitrecx.dazhixianxian.domain.po.DzUser;
import top.mitrecx.dazhixianxian.domain.vo.DzUserVO;
import top.mitrecx.dazhixianxian.domain.vo.PageUserRequest;
import top.mitrecx.dazhixianxian.mapper.DzUserMapper;
import top.mitrecx.dazhixianxian.service.IDzConfigService;
import top.mitrecx.dazhixianxian.service.IDzUserService;

import java.util.ArrayList;
import java.util.List;

import static top.mitrecx.dazhixianxian.common.BizCode.REQUEST_ARGUMENTS_ERROR;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cx
 * @since 2024-08-15
 */
@Service
@RequiredArgsConstructor
public class DzUserServiceImpl extends ServiceImpl<DzUserMapper, DzUser> implements IDzUserService {
    private final IDzConfigService dzConfigService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DzResponse<?> addUser(DzUserDTO user) {
        // 1. 校验参数
        if (user == null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "请求参数不能为空");
        }
        if (StringUtils.isBlank(user.getLoginName())) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "登录名不能为空");
        }
        LambdaQueryWrapper<DzUser> queryWrapper = Wrappers.lambdaQuery(DzUser.class)
                .eq(DzUser::getLoginName, user.getLoginName());
        DzUser exist = this.getOne(queryWrapper);
        if (exist != null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "用户名已存在");
        }

        // 2. 新增
        DzUser userPo = new DzUser();
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
    public DzResponse<?> updateUser(DzUserDTO request) {
        // 1. 校验参数
        if (request == null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "请求参数不能为空");
        }
        LambdaQueryWrapper<DzUser> queryWrapper = Wrappers.lambdaQuery(DzUser.class)
                .eq(DzUser::getLoginName, request.getLoginName());
        DzUser exist = this.getOne(queryWrapper);
        if (exist == null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "用户不存在");
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        // 2. 更新
        DzUser userPo = new DzUser();
        BeanUtils.copyProperties(request, userPo);
        this.updateById(userPo);
        return DzResponse.ok();
    }

    @Override
    public DzResponse<?> deleteUser(long userId) {
        // 1. 校验参数
        LambdaQueryWrapper<DzUser> queryWrapper = Wrappers.lambdaQuery(DzUser.class)
                .eq(DzUser::getUserId, userId);
        DzUser exist = this.getOne(queryWrapper);
        if (exist == null) {
            return DzResponse.fail(REQUEST_ARGUMENTS_ERROR, "用户不存在");
        }
        // 2. 删除
        exist.setDeleted(true);
        this.updateById(exist);
        return DzResponse.ok();
    }

    @Override
    public DzResponse<DzUserVO> detailsUser(Long userId) {
        // 1. 校验参数
        LambdaQueryWrapper<DzUser> queryWrapper = Wrappers.lambdaQuery(DzUser.class)
                .eq(DzUser::getUserId, userId);
        DzUser exist = this.getOne(queryWrapper);
        if (exist == null) {
            return DzResponse.<DzUserVO>builder().fail(REQUEST_ARGUMENTS_ERROR, "用户不存在").build();
        }
        // 2. 转换
        DzUserVO vo = new DzUserVO();
        BeanUtils.copyProperties(exist, vo);
        return DzResponse.ok(vo);
    }

    @Override
    public DzResponse<Page<DzUserVO>> pageUser(PageUserRequest request) {
        // 1. 校验参数
        if (request == null) {
            return DzResponse.<Page<DzUserVO>>builder().fail(REQUEST_ARGUMENTS_ERROR, "请求参数不能为空").build();
        }
        // 2. 分页查询
        Page<DzUser> page = new Page<>(request.getPageNumber(), request.getPageSize());
        LambdaQueryWrapper<DzUser> queryWrapper = Wrappers.lambdaQuery(DzUser.class);
        if (StringUtils.isNotBlank(request.getLoginName())) {
            queryWrapper.like(DzUser::getLoginName, request.getLoginName());
        }
        if (StringUtils.isNotBlank(request.getUsername())) {
            queryWrapper.like(DzUser::getUsername, request.getUsername());
        }
        if (StringUtils.isNotBlank(request.getEmail())) {
            queryWrapper.like(DzUser::getEmail, request.getEmail());
        }
        if (StringUtils.isNotBlank(request.getPhone())) {
            queryWrapper.like(DzUser::getPhone, request.getPhone());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            queryWrapper.eq(DzUser::getStatus, request.getStatus());
        }
        if (request.getDeleted() != null) {
            queryWrapper.eq(DzUser::getDeleted, request.getDeleted());
        }

        Page<DzUser> pageData = this.page(page, queryWrapper);
        // 3. 转换
        List<DzUserVO> list = new ArrayList<>();
        pageData.getRecords().forEach(user -> {
            DzUserVO vo = new DzUserVO();
            BeanUtils.copyProperties(user, vo);
            list.add(vo);
        });
        Page<DzUserVO> tPage = new Page<>(pageData.getCurrent(), pageData.getSize(), pageData.getTotal());
        tPage.setRecords(list);
        return DzResponse.ok(tPage);
    }
}
