package book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import book.pagination.Page;
import book.sqlprovider.ScriptSqlProviderImpl;
/**
 * 通用mapper接口，子接口只要继承该接口并添加一个说明表名的注解，
 * 即可获得单表增、删、查、改、批量增删改、分页查、按example查等多种方法。
 * */
public interface BaseMapper<ENTITY, EXAMPLE, PK> {
	@ResultType(Integer.class)
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "countByExample")
	int countByExample(@Param("example") EXAMPLE example);

	int deleteByExample(@Param("example") EXAMPLE example);
	@ResultType(Integer.class)
	@DeleteProvider(type = ScriptSqlProviderImpl.class, method = "deleteByPrimaryKey")
	int deleteByPrimaryKey(PK id);

	int insert(@Param("record") ENTITY record);

	int insertSelective(@Param("record") ENTITY record);

	@ResultMap("BaseResultMap")
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "selectByExample")
	List<ENTITY> selectByExample(@Param("example") EXAMPLE example);

	@ResultMap("BaseResultMap")
	//@Select("selectByPrimaryKey")
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "selectByPrimaryKey")
/*	@Select({"<script>select ",
		"id,name,label_id",
        "from sys_user",
        "<where>id = #{id,jdbcType=INTEGER}</where> ",
        "</script>"})*/
	ENTITY selectByPrimaryKey(@Param("id") PK id);

	int updateByExampleSelective(@Param("record") ENTITY record, @Param("example") EXAMPLE example);

	int updateByExample(@Param("record") ENTITY record, @Param("example") EXAMPLE example);

	int updateByPrimaryKeySelective(@Param("record") ENTITY record);

	int updateByPrimaryKey(@Param("record") ENTITY record);

	List<ENTITY> selectByExampleByPage(@Param("example") EXAMPLE example, @Param("page") Page page);

	int batchDeleteByExample(@Param("example") EXAMPLE example);

	int batchInsert(@Param("recordList") List<ENTITY> recordList);

	int batchInsertSelective(@Param("recordList") List<ENTITY> recordList);

	int batchUpdateByExampleSelective(@Param("record") ENTITY record, @Param("example") EXAMPLE example);

	int batchUpdateByExample(@Param("record") ENTITY record, @Param("example") EXAMPLE example);
}
