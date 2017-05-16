package book;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import experiment.mapper.UserMapper;
import experiment.mapper.UserRoleMidMapper;
import experiment.model.User;

public class MapperTest {
    SqlSession session;
    UserMapper userMapper;
    UserRoleMidMapper userRoleMidMapper;
    @Before
    public void before() throws IOException{
        String resource = "configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        
        XMLConfigBuilder xMLConfigBuilder = new XMLConfigBuilder(reader);
        Configuration configuration = xMLConfigBuilder.parse();
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        userMapper = session.getMapper(UserMapper.class);
        userRoleMidMapper = session.getMapper(UserRoleMidMapper.class);
    }
    @Test
    public void test1(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }
    @Test
    public void test2(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }
    @Test
    public void test3(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }
    @Test
    public void test4(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }
    @Test
    public void test5(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }
    @Test
    public void test6(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }
}
