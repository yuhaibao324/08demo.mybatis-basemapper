package book.sqlprovider;

import java.lang.reflect.Method;
import java.util.Set;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
/**封装ScriptSqlProvider创建动态sql语句时需要的信息，例如表名、主键映射，字段属性映射*/
public class MapperMeta {
	private String tablename;
	private ResultMap resultMap;
	private ResultMapping idResultMapping;
	private Set<String> mappedColumns;

	public MapperMeta(MapperBuilderAssistant assistant, Method method) {
		try {
		String namespace = assistant.getCurrentNamespace();
		Class<?> mapperClazz;
			mapperClazz = Class.forName(namespace);
	//method.getDeclaringClass();// book.mapper.UserMapper
		String mapperClazzName = mapperClazz.getName();
		Table tableAnno = mapperClazz.getAnnotation(Table.class);
		this.tablename = tableAnno.name();
		Configuration config = assistant.getConfiguration();
		this.resultMap = config.getResultMap(mapperClazzName + ".BaseResultMap");// book.mapper.UserMapper.BaseResultMap
		this.idResultMapping = this.resultMap.getIdResultMappings().get(0);
		this.mappedColumns = this.resultMap.getMappedColumns();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public String getTablename() {
		return tablename;
	}

	public ResultMap getResultMap() {
		return resultMap;
	}

	public ResultMapping getIdResultMapping() {
		return idResultMapping;
	}

	public Set<String> getMappedColumns() {
		return mappedColumns;
	}
}
