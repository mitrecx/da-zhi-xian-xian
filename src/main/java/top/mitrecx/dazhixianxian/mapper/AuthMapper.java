package top.mitrecx.dazhixianxian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.mitrecx.dazhixianxian.domain.dto.AuthDTO;
import top.mitrecx.dazhixianxian.domain.po.Auth;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2024-08-26
 */
public interface AuthMapper extends BaseMapper<Auth> {

    List<AuthDTO> findAuthByLoginName(@Param(value = "loginName") String loginName);
}
