package cn.howso.framework.mybatis.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.howso.framework.mybatis.provider.ProviderHelper;
import cn.howso.framework.mybatis.provider.ScriptSqlProviderImpl;
/**
 * 通常不直接使用该接口
 * */
public interface PrimaryKeyMapper<ENTITY,PK>{
	@ResultType(Integer.class)
	@DeleteProvider(type = ScriptSqlProviderImpl.class, method = "deleteByPrimaryKey")
	int deleteByPrimaryKey(@Param("id")PK id);//@Param("id")不要删除，约定名称为传参名为id，这样简单方便。
	
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
    @UpdateProvider(type = ScriptSqlProviderImpl.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(@Param("record") ENTITY record);

	@ResultType(Integer.class)
    @UpdateProvider(type = ScriptSqlProviderImpl.class, method = "updateByPrimaryKey")
	int updateByPrimaryKey(@Param("record") ENTITY record);
	
	@ResultType(Serializable.class)
	@InsertProvider(type = ScriptSqlProviderImpl.class, method = "insertSelectiveAndReturnPk")
	PK insertSelectiveAndReturnPk(@Param("record")ENTITY record);
}
