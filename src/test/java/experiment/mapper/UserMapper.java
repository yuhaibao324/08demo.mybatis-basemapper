package experiment.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Table;
import org.apache.ibatis.mapping.StatementType;

import cn.howso.framework.mybatis.mapper.BaseMapper;
import experiment.model.User;
import experiment.model.UserExample;
@Table(name="sys_user")
public interface UserMapper extends BaseMapper<User,UserExample,Integer>{
	@Override
	@SelectKey(before=true,statement="select nextVal(sys_user_id_seq)",keyColumn="id",keyProperty="id",resultType=Integer.class,statementType=StatementType.STATEMENT)
	int insertSelectiveSelectKey(@Param("record")User user);
	@ResultType(Integer.class)
	@Select("select count(*) from sys_user")
	Integer selectByMine();
}