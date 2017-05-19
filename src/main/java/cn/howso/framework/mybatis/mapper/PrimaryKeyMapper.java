package cn.howso.framework.mybatis.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderHelper;

import cn.howso.framework.mybatis.provider.ScriptSqlProviderImpl;
/**
 * 通常不直接使用该接口
 * */
public interface PrimaryKeyMapper<ENTITY,PK>{
	@ResultType(Integer.class)
	@DeleteProvider(type = ScriptSqlProviderImpl.class, method = "deleteByPrimaryKey")
	int deleteByPrimaryKey(@Param("id")PK id);//@Param("id")不要删除，约定名称为传参名为id，这样简单方便。
	
	@ResultMap(ProviderHelper.BASE_RESULT_MAP)
	@SelectProvider(type = ScriptSqlProviderImpl.class, method = "selectByPrimaryKey")
	ENTITY selectByPrimaryKey(@Param("id") PK id);//@Param("id")不要删除，约定名称为传参名为id，这样简单方便。
	
	@ResultType(Integer.class)
    @UpdateProvider(type = ScriptSqlProviderImpl.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ENTITY record);

	@ResultType(Integer.class)
    @UpdateProvider(type = ScriptSqlProviderImpl.class, method = "updateByPrimaryKey")
	int updateByPrimaryKey(ENTITY record);
	/**
	 * 子接口需要重写该接口，添加@ResultType,@InsertProvider,@SelectKey注解实现功能。
	 * 注意！！！注解方式使用@SelectKey，方法参数不能用@Param注解！！！
	 * */
	@ResultType(Serializable.class)
	@InsertProvider(type = ScriptSqlProviderImpl.class, method = "insertSelectiveSelectKey")
	int insertSelectiveSelectKey(ENTITY record);
}
