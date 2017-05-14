package book.sqlprovider;

import java.lang.reflect.Method;

import org.apache.ibatis.builder.MapperBuilderAssistant;

public interface ScriptSqlProvider {

	void setConfig(MapperBuilderAssistant assistant);

	String selectByPrimaryKey(Method method,Class<?> parameterType) throws ClassNotFoundException;
	String countByExample(Method method,Class<?> parameterType);

	String deleteByExample(Method method,Class<?> parameterType);

	String deleteByPrimaryKey(Method method,Class<?> parameterType) throws ClassNotFoundException;

	String insert(Method method,Class<?> parameterType);

	String insertSelective(Method method,Class<?> parameterType);

	String selectByExample(Method method,Class<?> parameterType);

	String updateByExampleSelective(Method method,Class<?> parameterType);

	String updateByExample(Method method,Class<?> parameterType);

	String updateByPrimaryKeySelective(Method method,Class<?> parameterType);

	String updateByPrimaryKey(Method method,Class<?> parameterType);

	String selectByExampleByPage(Method method,Class<?> parameterType);

	String batchDeleteByExample(Method method,Class<?> parameterType);

	String batchInsert(Method method,Class<?> parameterType);

	String batchInsertSelective(Method method,Class<?> parameterType);

	String batchUpdateByExampleSelective(Method method,Class<?> parameterType);

	String batchUpdateByExample(Method method,Class<?> parameterType);
}
