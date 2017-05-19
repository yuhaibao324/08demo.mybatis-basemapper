package experiment.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Table;
import org.apache.ibatis.mapping.StatementType;

import cn.howso.framework.mybatis.mapper.BaseMapper;
import cn.howso.framework.mybatis.provider.ScriptSqlProviderImpl;
import experiment.model.User;
import experiment.model.UserExample;
@Table(name="sys_user")
public interface UserMapper extends BaseMapper<User,UserExample,Integer>{
	@Override
	@ResultType(Serializable.class)
	@SelectKey(before=true,statement="select nextVal('sys_user_id_seq')",keyColumn="id",keyProperty="id",resultType=Integer.class,statementType=StatementType.PREPARED)
    @InsertProvider(type = ScriptSqlProviderImpl.class, method = "insertSelectiveSelectKey")
	int insertSelectiveSelectKey(User user);
	@ResultType(Integer.class)
	@Select("select count(*) from sys_user")
	Integer selectByMine();
}