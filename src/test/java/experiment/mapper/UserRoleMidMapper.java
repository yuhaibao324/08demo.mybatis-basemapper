package experiment.mapper;

import org.apache.ibatis.annotations.Table;

import cn.howso.framework.mybatis.mapper.ExampleMapper;
import experiment.model.UserRoleMid;
import experiment.model.UserRoleMidExample;
@Table(name="sys_user_role_mid")
public interface UserRoleMidMapper extends ExampleMapper<UserRoleMid,UserRoleMidExample>{
}