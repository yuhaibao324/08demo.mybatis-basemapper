package book;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Table;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })

public class ExecutorPlugin implements Interceptor {
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		Method method = invocation.getMethod();
		Object[] args = invocation.getArgs();
		Executor executor = (Executor) target;
		MappedStatement statement = (MappedStatement) args[0];
		Configuration configuration = statement.getConfiguration();
		Object parameterObject = args[1];
		statement.getSqlSource().getBoundSql(parameterObject);
		if(method.getName().equals("query")){
			RowBounds rowBounds = (RowBounds) args[2];
			ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
			Class<?> parameterType = Map.class;
			Map<String, Object> additionalParameters = (Map<String, Object>) parameterObject;
			BoundSql boundSql = statement.getBoundSql(parameterObject);
			SqlNode textSqlNode = new TextSqlNode("<script>select * from sys_user <where> id<5</where></script>");
			DynamicSqlSource sqlSrouce = new DynamicSqlSource(configuration, textSqlNode );
			
			RawSqlSource rawsql = new RawSqlSource(configuration, "select * from sys_user <where> id<5</where>", parameterType);
			//args[0]= statement = new MappedStatement.Builder(configuration,statement.getId(), sqlSrouce, SqlCommandType.SELECT).build();

			String sql = statement.getBoundSql(parameterObject).getSql();
			if("selectByPrimaryKey".equals(sql)){
				SqlSource sqlSource = new SqlSourceBuilder(configuration).parse("select * from sys_user <where> id=#{id}</where>", parameterType, additionalParameters);
				args[0] = new MappedStatement.Builder(configuration,statement.getId(), sqlSource, SqlCommandType.SELECT).build();
				/*System.out.println("haha");
				String resource = statement.getResource();
				List<ResultMap> resultMapList = statement.getResultMaps();
				resultMapList.stream().filter(item->{
					
					return item.getId().endsWith(".BaseResultMap");
				}).findFirst().ifPresent((resultMap)->{
					Class<?> clazz = resultMap.getType();
					System.out.println(clazz.getName());
					Table table = clazz.getAnnotation(Table.class);
					String tableName = table.name();
					resultMap.getResultMappings();
					List<ResultMapping> resultMappings = resultMap.getPropertyResultMappings();
					List<String> columns = new ArrayList<>(resultMappings.size());
					List<String> properties = new ArrayList<>(resultMappings.size());
					resultMappings.stream().forEach(mapping->{
						columns.add(mapping.getColumn());
						properties.add(mapping.getProperty());
					});
					StringBuilder newSql = new StringBuilder("select ");
					newSql.append(String.join(" , ", columns));
					newSql.append(" from ").append(tableName).append(" where id=#{id}");
					//newSql.append(String.join(" , ", properties));
					//MetaObject metaObject = SystemMetaObject.forObject(boundSql);
					//metaObject.setValue("sql", newSql.toString());
					MetaObject statementMetaObject = SystemMetaObject.forObject(statement);
					System.out.println(statement);
					System.out.println(boundSql);
					args[0] = newMappedStatement(statement);
				});*/
			}
		}else{
			
		}
		
		
		System.out.println("--");
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		System.out.println(properties);
	}
	 public MappedStatement newMappedStatement(MappedStatement ms) {
		 ms.getBoundSql("");
	        //下面是新建的过程，考虑效率和复用对象的情况下，这里最后生成的ms可以缓存起来，下次根据 ms.getId() + "_" + getShortName(resultType) 直接返回 ms,省去反复创建的过程
	        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "_new", ms.getSqlSource(), ms.getSqlCommandType());
	        builder.resource(ms.getResource());
	        builder.fetchSize(ms.getFetchSize());
	        builder.statementType(ms.getStatementType());
	        builder.keyGenerator(ms.getKeyGenerator());
	        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
	            StringBuilder keyProperties = new StringBuilder();
	            for (String keyProperty : ms.getKeyProperties()) {
	                keyProperties.append(keyProperty).append(",");
	            }
	            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
	            builder.keyProperty(keyProperties.toString());
	        }
	        builder.timeout(ms.getTimeout());
	        builder.parameterMap(ms.getParameterMap());
	        //count查询返回值int
	        builder.resultMaps( ms.getResultMaps());
	        builder.resultSetType(ms.getResultSetType());
	        builder.cache(ms.getCache());
	        builder.flushCacheRequired(ms.isFlushCacheRequired());
	        builder.useCache(ms.isUseCache());
	        return builder.build();
	    }
}
