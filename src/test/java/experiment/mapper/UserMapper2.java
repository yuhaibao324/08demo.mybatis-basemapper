package experiment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import cn.howso.framework.mybatis.pagination.IndexPage;
import cn.howso.framework.mybatis.sqlprovider.ScriptSqlProviderImpl;
import cn.howso.framework.mybatis.sqlprovider.Table;
import experiment.model.User;
import experiment.model.UserExample;
@Table(name="sys_user")
public interface UserMapper2{
	@ResultType(Integer.class)
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "countByExample")
	int countByExample(@Param("example") UserExample example);

	int deleteByExample(@Param("example") UserExample example);
	@ResultType(Integer.class)
	@DeleteProvider(type = ScriptSqlProviderImpl.class, method = "deleteByPrimaryKey")
	int deleteByPrimaryKey(@Param("id") Integer id);

	int insert(@Param("record") User record);

	int insertSelective(@Param("record") User record);

	@ResultMap("BaseResultMap")
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "selectByExample")
	List<User> selectByExample(@Param("example") UserExample example);

	@ResultMap("BaseResultMap")
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "selectByPrimaryKey")
	User selectByPrimaryKey(@Param("id") Integer id);

	int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

	int updateByExample(@Param("record") User record, @Param("example") UserExample example);

	int updateByPrimaryKeySelective(@Param("record") User record);

	int updateByPrimaryKey(@Param("record") User record);

	List<User> selectByExampleByPage(@Param("example") UserExample example, @Param("page") IndexPage page);

	int batchDeleteByExample(@Param("example") UserExample example);

	int batchInsert(@Param("recordList") List<User> recordList);

	int batchInsertSelective(@Param("recordList") List<User> recordList);

	int batchUpdateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

	int batchUpdateByExample(@Param("record") User record, @Param("example") UserExample example);
}