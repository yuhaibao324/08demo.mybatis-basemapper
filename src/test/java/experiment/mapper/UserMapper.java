package experiment.mapper;

import org.apache.ibatis.annotations.Table;

import cn.howso.framework.mybatis.mapper.xml.BaseMapper;
import experiment.model.User;
import experiment.model.UserExample;
@Table(name="sys_user")
public interface UserMapper extends BaseMapper<User,UserExample,Integer>{
	/*@Override
	@ResultType(Serializable.class)
	@SelectKey(before=true,statement="select nextVal('sys_user_id_seq')",keyColumn="id",keyProperty="id",resultType=Integer.class,statementType=StatementType.PREPARED)
    @InsertProvider(type = ScriptSqlProviderImpl.class, method = "insertSelectiveSelectKey")
	int insertSelectiveSelectKey(User user);
	@ResultType(Integer.class)
	@Select("select count(*) from sys_user")
	Integer selectByMine();*/
}