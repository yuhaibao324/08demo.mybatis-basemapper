package book.mybatis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import book.sqlprovider.DefaultSqlProvider;

public class PluginHelper {
	private static final Map<String, Method> providerMethods = new HashMap<>();

	private static void init() {
		Class<DefaultSqlProvider> sqlProviderClazz = DefaultSqlProvider.class;
		Method[] methods = sqlProviderClazz.getDeclaredMethods();
		for (Method method : methods) {
			providerMethods.put(method.getName(), method);
		}
	}

	public static Method getProviderMethod(String method) {
		return providerMethods.get(method);
	}

	static {
		init();
	}
	private StatementHandler statementHandler;
	private MappedStatement mappedStatement;
	private Configuration configuration;
	private StatementHandler delegate;
	private ResultMap baseResultMap;
	private BoundSql boundSql;
	public PluginHelper(StatementHandler statementHandler) {
		this.statementHandler = statementHandler;
	}

	public void replaceSqlAndParam()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MetaObject statementHanderMO = SystemMetaObject.forObject(statementHandler);
		mappedStatement = (MappedStatement) statementHanderMO.getValue("delegate.mappedStatement");
		String mappedStatementId = mappedStatement.getId();// 包名.类名.selectByPrimaryKey
		int lastIndexOfDot = mappedStatementId.lastIndexOf('.');
		Method providerMethod = PluginHelper.getProviderMethod(mappedStatementId.substring(lastIndexOfDot + 1));
		if (providerMethod == null) {
			return;
		}
		MetaObject mappedStatementMO = SystemMetaObject.forObject(mappedStatement);
		configuration = (Configuration) mappedStatementMO.getValue("configuration");
		delegate = (StatementHandler) statementHanderMO.getValue("delegate");
		String mapperClazzName = mappedStatementId.substring(0, lastIndexOfDot);// 包名.类名
		this.baseResultMap = configuration.getResultMap(mapperClazzName + ".BaseResultMap");
		//Class<?> domainClazz = baseResultMap.getType();
		//Table table = domainClazz.getAnnotation(Table.class);
		//String tableName = table.name();
		baseResultMap.getResultMappings();
		//List<ResultMapping> resultMappings = baseResultMap.getPropertyResultMappings();
		//List<String> columns = new ArrayList<>(resultMappings.size());
		/*List<String> properties = new ArrayList<>(resultMappings.size());
		resultMappings.stream().forEach(mapping -> {
			columns.add(mapping.getColumn());
			properties.add(mapping.getProperty());
		});
		String baseColumnList = String.join(" , ", columns);*/
		boundSql = delegate.getBoundSql();
		providerMethod.invoke(new DefaultSqlProvider(), this);
	}

	public ResultMap getBaseResultMap() {
		return this.baseResultMap;
	}

	public static Map<String, Method> getProvidermethods() {
		return providerMethods;
	}

	public StatementHandler getStatementHandler() {
		return statementHandler;
	}

	public MappedStatement getMappedStatement() {
		return mappedStatement;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public StatementHandler getDelegate() {
		return delegate;
	}

	public BoundSql getBoundSql() {
		return boundSql;
	}
}
