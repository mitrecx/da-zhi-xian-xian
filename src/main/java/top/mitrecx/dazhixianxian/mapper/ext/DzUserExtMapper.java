package top.mitrecx.dazhixianxian.mapper.ext;

import org.apache.ibatis.annotations.Param;
import top.mitrecx.dazhixianxian.domain.po.DzUser;
import top.mitrecx.dazhixianxian.mapper.DzUserMapper;

public interface DzUserExtMapper extends DzUserMapper {
    DzUser selectByLoginName(@Param("loginName") String loginName);
}
