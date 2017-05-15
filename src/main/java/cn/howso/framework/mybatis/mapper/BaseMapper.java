package cn.howso.framework.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.howso.framework.mybatis.pagination.Page;
import cn.howso.framework.mybatis.sqlprovider.ProviderHelper;
import cn.howso.framework.mybatis.sqlprovider.ScriptSqlProviderImpl;
/**
 * 通用mapper接口，子接口只要继承该接口并添加一个说明表名的注解，
 * 即可获得单表增、删、查、改、批量增删改、分页查、按example查等多种方法。
 * */
public interface BaseMapper<ENTITY, EXAMPLE, PK> {
	@ResultType(Integer.class)
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "countByExample")
	int countByExample(@Param("example") EXAMPLE example);
	
	@ResultType(Integer.class)
    @DeleteProvider(type = ScriptSqlProviderImpl.class, method = "deleteByExample")
	int deleteByExample(@Param("example") EXAMPLE example);
	
	@ResultType(Integer.class)
	@DeleteProvider(type = ScriptSqlProviderImpl.class, method = "deleteByPrimaryKey")
	int deleteByPrimaryKey(@Param("id")PK id);//@Param("id")不要删除，约定名称为传参名为id，这样简单方便。
	
	@ResultType(Integer.class)
    @InsertProvider(type = ScriptSqlProviderImpl.class, method = "insert")
	int insert(@Param("record")ENTITY record);
	
	@ResultType(Integer.class)
    @InsertProvider(type = ScriptSqlProviderImpl.class, method = "insertSelective")
	int insertSelective(@Param("record")ENTITY record);

	@ResultMap(ProviderHelper.BASE_RESULT_MAP)
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "selectByExample")
	List<ENTITY> selectByExample(@Param("example") EXAMPLE example);

	@ResultMap(ProviderHelper.BASE_RESULT_MAP)
	//@Select("selectByPrimaryKey")
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "selectByPrimaryKey")
/*	@Select({"<script>select ",
		"id,name,label_id",
        "from sys_user",
        "<where>id = #{id,jdbcType=INTEGER}</where> ",
        "</script>"})*/
	ENTITY selectByPrimaryKey(@Param("id") PK id);//@Param("id")不要删除，约定名称为传参名为id，这样简单方便。

	@ResultType(Integer.class)
	@UpdateProvider(type = ScriptSqlProviderImpl.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ENTITY record, @Param("example") EXAMPLE example);
	
	@ResultType(Integer.class)
    @UpdateProvider(type = ScriptSqlProviderImpl.class, method = "updateByExample")
	int updateByExample(@Param("record") ENTITY record, @Param("example") EXAMPLE example);

	@ResultType(Integer.class)
    @UpdateProvider(type = ScriptSqlProviderImpl.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(@Param("record") ENTITY record);

	@ResultType(Integer.class)
    @UpdateProvider(type = ScriptSqlProviderImpl.class, method = "updateByPrimaryKey")
	int updateByPrimaryKey(@Param("record") ENTITY record);
	
	@ResultMap(ProviderHelper.BASE_RESULT_MAP)
    @SelectProvider(type = ScriptSqlProviderImpl.class, method = "selectByExampleByPage")
	List<ENTITY> selectByExampleByPage(@Param("example") EXAMPLE example, @Param("page") Page page);

	@ResultType(Integer.class)
    @InsertProvider(type = ScriptSqlProviderImpl.class, method = "batchInsert")
	int batchInsert(@Param("recordList") List<ENTITY> recordList);
	
	@ResultType(Integer.class)
    @InsertProvider(type = ScriptSqlProviderImpl.class, method = "batchInsertSelective")
	int batchInsertSelective(@Param("recordList") List<ENTITY> recordList);

}
