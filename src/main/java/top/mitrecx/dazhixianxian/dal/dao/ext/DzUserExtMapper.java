package top.mitrecx.dazhixianxian.dal.dao.ext;

import org.apache.ibatis.annotations.Param;
import top.mitrecx.dazhixianxian.dal.dao.DzUserMapper;
import top.mitrecx.dazhixianxian.dal.entity.DzUser;

public interface DzUserExtMapper extends DzUserMapper {
    DzUser selectByLoginName(@Param("loginName") String loginName);
}
