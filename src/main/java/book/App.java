package book;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import book.mapper.UserMapper;
import book.model.UserExample;

public class App {
	public static void main(String[] args) throws IOException {
		String resource = "configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		
		XMLConfigBuilder xMLConfigBuilder = new XMLConfigBuilder(reader);
		Configuration configuration = xMLConfigBuilder.parse();
		SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
		//SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sqlSessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		//User user = userMapper.selectByPrimaryKey(1);
		//user = userMapper.selectByPrimaryKey(1);
		UserExample example = new UserExample();
		example.createCriteria().andIdIn(Arrays.asList(new Integer[]{1,2,3}));//.andIdEqualTo(1).andIdBetween(0, 2);
		//example.or().andNameIn(Arrays.asList(new String[]{"1","2","3"})).andLabelIdIsNull();
		//example.createCriteria().andNameIn(Arrays.asList(new String[]{"1","2","3"}));
		//int count = userMapper.countByExample(example );
		//System.out.println(count);
		int list = userMapper.countByExample(example);
		System.out.println(list);
		//int i = userMapper.deleteByPrimaryKey(1);
		//System.out.println(i);
		//session.commit();
	}
}
