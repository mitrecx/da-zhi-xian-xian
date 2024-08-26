package top.mitrecx.dazhixianxian.service.impl;

import top.mitrecx.dazhixianxian.domain.po.Auth;
import top.mitrecx.dazhixianxian.mapper.AuthMapper;
import top.mitrecx.dazhixianxian.service.IAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2024-08-26
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements IAuthService {

}
