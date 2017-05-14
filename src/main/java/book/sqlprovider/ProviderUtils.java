package book.sqlprovider;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;
/**修改mybatis源码，但是不能让它的源码对我们写的代码依赖太多，所以就让它只依赖这一个类。*/
public class ProviderUtils {

	public static String getSql(MapperBuilderAssistant assistant,Method method,Class<?> parameterType, Annotation provider) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		 Class<?> providerType = (Class<?>) provider.getClass().getMethod("type").invoke(provider);
	      String providerMethodName = (String) provider.getClass().getMethod("method").invoke(provider);
	      Parameter[] ps = method.getParameters();
	      Object o = method.getGenericParameterTypes();
	      o = method.getParameterTypes();
	      o = method.getTypeParameters();
	      o = method.getGenericReturnType();
	      Method providerMethod = null;
	      for (Method m : providerType.getMethods()) {
	          if (providerMethodName.equals(m.getName())) {
	            if (m.getReturnType() == String.class) {
	              if (providerMethod != null){
	                throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
	                        + providerMethodName + "' is found multiple in SqlProvider '" + providerType.getName()
	                        + "'. Sql provider method can not overload.");
	              }
	              providerMethod = m;
	             String[] providerMethodArgumentNames = new ParamNameResolver(assistant.getConfiguration(), m).getNames();
	            }
	          }
	        }
	      Class<?>[] parameterTypes = providerMethod.getParameterTypes();
	      String sql;
	      ScriptSqlProvider scriptSqlProvider = (ScriptSqlProvider) providerType.newInstance();
	      scriptSqlProvider.setConfig(assistant);
	      String[] providerMethodArgumentNames= new ParamNameResolver(assistant.getConfiguration(), providerMethod).getNames();
	      //scriptSqlProvider.selectByPrimaryKey(method,parameterType,parameterTypes,providerMethodArgumentNames);
	        sql = (String) providerMethod.invoke(scriptSqlProvider,method,parameterType);
		return sql;
	}

	public static boolean isScriptSqlProvider(Annotation sqlProviderAnnotation) {
		// TODO Auto-generated method stub
		return true;
	}
}
