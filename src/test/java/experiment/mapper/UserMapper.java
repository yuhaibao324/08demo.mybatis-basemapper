package experiment.mapper;

import cn.howso.framework.mybatis.mapper.BaseMapper;
import cn.howso.framework.mybatis.sqlprovider.Table;
import experiment.model.User;
import experiment.model.UserExample;
@Table(name="sys_user")
public interface UserMapper extends BaseMapper<User,UserExample,Integer>{
}