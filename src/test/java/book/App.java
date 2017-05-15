package book;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import cn.howso.framework.mybatis.pagination.IndexPage;
import experiment.mapper.UserMapper;
import experiment.model.User;
import experiment.model.UserExample;

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
		example.createCriteria().andIdIn(Arrays.asList(new Integer[]{-1,1,2,3,4,5,6}));//.andIdEqualTo(1).andIdBetween(0, 2);
		//example.or().andNameIn(Arrays.asList(new String[]{"1","2","3"})).andLabelIdIsNull();
		//example.createCriteria().andNameIn(Arrays.asList(new String[]{"1","2","3"}));
		//int count = userMapper.countByExample(example );
		//System.out.println(count);
		//int n = userMapper.deleteByExample(example);
		//System.out.println(n);
		User record = new User();
		record.setName("aidehua");
		IndexPage page =  IndexPage.of(1, 2);
        //record.setId(-1);
        //int i = userMapper.deleteByPrimaryKey(1);
		//System.out.println(i);
		List<User> res = userMapper.selectByExampleByPage(example,page );
		System.out.println(res);
		session.commit();
	}
}
