package book;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import cn.howso.framework.mybatis.pagination.LimitPage;
import experiment.mapper.UserMapper;
import experiment.model.User;
import experiment.model.UserExample;

public class MyApp{
	public static void main(String[] args) throws IOException {
		String resource = "configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		
		XMLConfigBuilder xMLConfigBuilder = new XMLConfigBuilder(reader);
		Configuration configuration = xMLConfigBuilder.parse();
		SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
		//SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sqlSessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		User user = new User();
		user.setName("mayun");
		/*userMapper.insertSelectiveSelectKey(user);
		System.out.println(user.getId());*/
		List<User> recordList = new ArrayList<>();
		user.setId(-4);
		recordList.add(user);
        //userMapper.batchInsertSelective(recordList);
        
        UserExample example = new UserExample();
        example.createCriteria().andNameLike("ma%").andIdEqualTo(-4);
        //userMapper.batchInsert(recordList);
		//int count = userMapper.countByExample(example );
		//System.out.println(count);
        
       // userMapper.deleteByExample(example);
        
        //userMapper.insert(user);
        
        //userMapper.insertSelective(user);
       // userMapper.selectByExample(example);
        //userMapper.selectByExampleByPage(example, LimitPage.of(10, 0));
        //userMapper.selectByPrimaryKey(-4);
        //userMapper.updateByExampleSelective(user, example);
        //userMapper.updateByPrimaryKey(user);
        user.setLabelId(1);
        userMapper.updateByPrimaryKeySelective(user);
		session.commit();
	}

}
