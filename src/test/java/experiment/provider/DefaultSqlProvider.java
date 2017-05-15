package experiment.provider;

import javax.persistence.Table;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import experiment.plugin.PluginHelper;
/**该类所有的方法都修改boundSql的sql和parameterObject。它并不是配合Provider注解使用的，
 * 而是配合Select等注解使用。
 * 该类和ScriptSqlProvider有相似的地方，都修改并提供sql语句，但是两者能够获取到的上下文不同，
 * 最重要的是前者可以支持script，后者貌似无法实现。
 * */
public class DefaultSqlProvider {
	public void selectByPrimaryKey(PluginHelper helper) {
		BoundSql boundSql = helper.getBoundSql();
		MetaObject boundSqlMetaObject = SystemMetaObject.forObject(boundSql);
		String[] columns = helper.getBaseResultMap().getMappedColumns().toArray(new String[]{});
		Class<?> domainClazz = helper.getBaseResultMap().getType();
		Table table = domainClazz.getAnnotation(Table.class);
		String tableName = table.name();
		String sql = "select " + String.join(",", columns)+" from "+tableName+" where id=1";
		boundSqlMetaObject.setValue("sql", sql);
	}

	public void countByExample(PluginHelper helper) {
	}

	public void deleteByExample(PluginHelper helper) {
	}

	public void deleteByPrimaryKey(PluginHelper helper) {
	}

	public void insert(PluginHelper helper) {
		
	}

	public void insertSelective(PluginHelper helper) {
		
	}

	public void selectByExample(PluginHelper helper) {
		
	}

	public void updateByExampleSelective(PluginHelper helper) {
		
	}

	public void updateByExample(PluginHelper helper) {
		
	}

	public void updateByPrimaryKeySelective(PluginHelper helper) {
		
	}

	public void updateByPrimaryKey(PluginHelper helper) {
		
	}

	public void selectByExampleByPage(PluginHelper helper) {
		
	}

	public void batchDeleteByExample(PluginHelper helper) {
		
	}

	public void batchInsert(PluginHelper helper) {
		
	}

	public void batchInsertSelective(MapperMethod.ParamMap<Object> mm) {
		
	}

	public void batchUpdateByExampleSelective(MapperMethod.ParamMap<Object> mm) {
		
	}

	public void batchUpdateByExample(MapperMethod.ParamMap<Object> mm) {
		
	}
}
