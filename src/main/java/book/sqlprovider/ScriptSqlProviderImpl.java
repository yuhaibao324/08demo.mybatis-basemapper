package book.sqlprovider;

import java.lang.reflect.Method;
import java.util.Set;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.ResultMapping;
/**支持mapper方法注解使用SqlProvider实现带标签的动态sql。
 * SELECT注解使用script支持带标签的写法,但是注解中没法用java代码编程，它只能是字符串常量。
 * SelectProvider支持java代码编程，但是不支持script。为了结合静态的xml和动态的java的优势，
 * 修改了mybatis的源代码*/
public class ScriptSqlProviderImpl implements ScriptSqlProvider {
	MapperBuilderAssistant assistant;
	@Override
	public void setConfig(MapperBuilderAssistant assistant) {
		this.assistant = assistant;
	}
	@Override
	public String selectByPrimaryKey(Method method,Class<?> parameterType) throws ClassNotFoundException {
		MapperMeta meta = new MapperMeta(assistant,method);
		Set<String> columns = meta.getMappedColumns();
		ResultMapping idResultMapping = meta.getIdResultMapping();
		
		StringBuilder sql = new StringBuilder();
		sql.append("<script>");
		sql.append(" select ");
		sql.append(String.join(",", columns.toArray(new String[]{})));
		sql.append(" from ");
		sql.append(meta.getTablename());
		sql.append(" where "+idResultMapping.getColumn()+"=#{"+idResultMapping.getProperty()+",jdbcType="+idResultMapping.getJdbcType()+"}");
		sql.append("</script>");
		return sql.toString();
		//return "<script>select * from sys_user <where>id=#{id}</where></script>";
	}
	@Override
	public String countByExample(Method method, Class<?> parameterType) {
		MapperMeta meta = new MapperMeta(assistant, method);
		StringBuilder sql = new StringBuilder("<script>")
				.append("select count(*) from ").append(meta.getTablename())
		.append(" <if test=\"_parameter != null\">")
		.append(exampleWhereClause(meta))
				.append(" </if>");
		return sql.append("</script>").toString();
	}
	@Override
	public String deleteByExample(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String deleteByPrimaryKey(Method method, Class<?> parameterType) {
		MapperMeta meta = new MapperMeta(assistant, method);
		ResultMapping idMP = meta.getIdResultMapping();
		StringBuilder sql = new StringBuilder("<script>")
				.append("delete from ").append(meta.getTablename())
		   .append(" where "+ idMP.getColumn()+"= #{"+idMP.getProperty()+"}");
		return sql.append("</script>").toString();
	}
	@Override
	public String insert(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String insertSelective(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String selectByExample(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String updateByExampleSelective(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String updateByExample(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String updateByPrimaryKeySelective(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String updateByPrimaryKey(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String selectByExampleByPage(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String batchDeleteByExample(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String batchInsert(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String batchInsertSelective(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String batchUpdateByExampleSelective(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String batchUpdateByExample(Method method, Class<?> parameterType) {
		// TODO Auto-generated method stub
		return null;
	}
	private String exampleWhereClause(MapperMeta meta){
		StringBuilder sql = new StringBuilder();
		 sql.append(" <where>").append("<foreach collection=\"example.oredCriteria\" item='criteria' separator=\"or\">")//xml中可以用单引号或双引号包含属性值
				 .append("<if test=\"criteria.valid\">")
				 .append("<trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">")
				 .append("<foreach collection=\"criteria.criteria\" item=\"criterion\">")
				.append("<choose>")
				.append("<when test=\"criterion.noValue\">")
				 .append("  and ${criterion.condition}")
				.append("  </when>")
				 .append("  <when test=\"criterion.singleValue\">")
				 .append("    and ${criterion.condition} #{criterion.value}")
				 .append(" </when>")
			 .append("   <when test=\"criterion.betweenValue\">")
				 .append(" and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}")
				 .append("</when>")
						 .append("<when test=\"criterion.listValue\">")
						 .append(" and ${criterion.condition}")
				 .append("  <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">")
							 .append("    #{listItem}")
				 .append("   </foreach>")
					 .append("</when>")
					 .append("</choose>")
					 .append("  </foreach>")
					 .append(" </trim>")
						 .append("</if>")
			 .append("</foreach>")
  .append(" </where>");
  return sql.toString();
	}
}
