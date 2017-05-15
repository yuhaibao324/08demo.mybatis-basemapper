package cn.howso.framework.mybatis.sqlprovider;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderHelper {
    public static final String BASE_RESULT_MAP = "BaseResultMap";
    public static final Logger logger = LoggerFactory.getLogger(ProviderHelper.class);
    //有些属性暂时没用到，但是不要删除，因为后续版本中可能用于优化。
    private MapperBuilderAssistant assistant;
    private Method method;
    private Annotation sqlProviderAnnotation;
    private Class<? extends Annotation> sqlProviderAnnotationType;
    private Class<?> sqlProviderClazz;
    private String sqlProviderMethodName;
    private String tablename;
    private Set<String> mappedColumns;
    private Set<String> mappedProperties;
    private List<ResultMapping> resultMappings;
    private ResultMapping idResultMapping;
    private ResultMap resultMap;

    public static boolean isScriptSqlProvider(Annotation sqlProviderAnnotation,
            Class<? extends Annotation> sqlProviderAnnotationType)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException, ClassNotFoundException {
        Class<?> providerClazz = (Class<?>) sqlProviderAnnotationType.getMethod("type").invoke(sqlProviderAnnotation);
        ScriptSqlProvider anno = providerClazz.getAnnotation(ScriptSqlProvider.class);
        return anno != null;
    }

    public String getSql()
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method providerMethod = null;
        for (Method m : sqlProviderClazz.getMethods()) {
            if (sqlProviderMethodName.equals(m.getName())) {
                if (m.getReturnType() == String.class) {
                    if (providerMethod != null) {
                        throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
                                + sqlProviderMethodName + "' is found multiple in SqlProvider '"
                                + sqlProviderClazz.getName() + "'. Sql provider method can not overload.");
                    }
                    providerMethod = m;
                }
            }
        }
        Object sqlProvider = sqlProviderClazz.newInstance();
        if(logger.isDebugEnabled()){
            logger.debug(sqlProviderMethodName+" begin");
        }
        String sql = (String) providerMethod.invoke(sqlProvider, this);
        if(logger.isDebugEnabled()){
            logger.debug(sqlProviderMethodName+" end");
        }
        return sql;
    }

    /**
     * 静态工厂方法，可以支持缓存
     * 
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public static ProviderHelper create(MapperBuilderAssistant assistant, Method method,
            Annotation sqlProviderAnnotation, Class<? extends Annotation> sqlProviderAnnotationType)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException, ClassNotFoundException {
        ProviderHelper helper = new ProviderHelper();
        helper.assistant = assistant;
        helper.method = method;
        helper.sqlProviderAnnotation = sqlProviderAnnotation;
        helper.sqlProviderAnnotationType = sqlProviderAnnotationType;
        Class<?> providerClazz = (Class<?>) sqlProviderAnnotationType.getMethod("type").invoke(sqlProviderAnnotation);
        helper.sqlProviderClazz = providerClazz;
        helper.sqlProviderMethodName = (String) sqlProviderAnnotationType.getMethod("method")
                .invoke(sqlProviderAnnotation);
        String namespace = assistant.getCurrentNamespace();
        Class<?> mapperClazz = Class.forName(namespace);
        String mapperClazzName = mapperClazz.getName();
        Table tableAnno = mapperClazz.getAnnotation(Table.class);
        helper.tablename = tableAnno.name();
        Configuration config = assistant.getConfiguration();
        helper.resultMap = config.getResultMap(mapperClazzName + "."+BASE_RESULT_MAP);// book.mapper.UserMapper.BaseResultMap
        helper.resultMappings = helper.resultMap.getResultMappings();
        helper.idResultMapping = helper.resultMap.getIdResultMappings().get(0);
        helper.mappedColumns = helper.resultMap.getMappedColumns();
        return helper;
    }

    public MapperBuilderAssistant getAssistant() {
        return assistant;
    }

    public Method getMethod() {
        return method;
    }

    public Annotation getSqlProviderAnnotation() {
        return sqlProviderAnnotation;
    }

    public Class<? extends Annotation> getSqlProviderAnnotationType() {
        return sqlProviderAnnotationType;
    }

    public Class<?> getSqlProviderClazz() {
        return sqlProviderClazz;
    }

    public String getSqlProviderMethodName() {
        return sqlProviderMethodName;
    }

    public String getTablename() {
        return tablename;
    }

    public Set<String> getMappedColumns() {
        return mappedColumns;
    }

    public Set<String> getMappedProperties() {
        return mappedProperties;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }

    public ResultMapping getIdResultMapping() {
        return idResultMapping;
    }

    public ResultMap getResultMap() {
        return resultMap;
    }

}
