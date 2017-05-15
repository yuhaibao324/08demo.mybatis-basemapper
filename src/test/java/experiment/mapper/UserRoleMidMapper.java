package experiment.mapper;

import cn.howso.framework.mybatis.mapper.ExampleMapper;
import cn.howso.framework.mybatis.provider.Table;
import experiment.model.UserRoleMid;
import experiment.model.UserRoleMidExample;
@Table(name="sys_user_role_mid")
public interface UserRoleMidMapper extends ExampleMapper<UserRoleMid,UserRoleMidExample>{
}