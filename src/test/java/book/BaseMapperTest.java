package book;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import experiment.mapper.UserMapper;
import experiment.model.User;
import experiment.model.UserExample;

public class BaseMapperTest {

    static UserMapper userMapper;
    static SqlSession session;
    @BeforeClass//before和beforeClass的区别，前者在每个测试方法执行前执行一遍，后者在所有测试方法执行前执行一遍。
    public static void beforeClass() throws IOException {
        String resource = "configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);

        XMLConfigBuilder xMLConfigBuilder = new XMLConfigBuilder(reader);
        Configuration configuration = xMLConfigBuilder.parse();
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        // SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        userMapper = session.getMapper(UserMapper.class);
    }
    @AfterClass
    public static void afterClass(){
        session.commit();
    }
    @Test
    public void selectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user.getName());
    }
    @Test
    public void countByExample() {
        UserExample example = new UserExample();
        example.createCriteria().andNameLike("ma%").andIdNotEqualTo(-4);
        int count = userMapper.countByExample(example);
        System.out.println(count);
    }
    @Test
    public void deleteByExample() {
        UserExample example = new UserExample();
        example.createCriteria().andNameLike("ma%").andIdEqualTo(-4);
        int count = userMapper.deleteByExample(example);
        System.out.println(count);
    }
    @Test
    public void deleteByPrimaryKey() {
        int count = userMapper.deleteByPrimaryKey(-1);
        System.out.println(count);
    }
    @Test
    public void insert() {
        User user = new User();
        user.setName("mayun");
        user.setId(-4);
        int count = userMapper.insert(user);
        System.out.println(count);
    }
    @Test
    public void insertSelective() {
        User user = new User();
        user.setName("mayun");
        int count = userMapper.insertSelective(user);
        System.out.println(count);
    }
    @Test
    public void selectByExample() {
        UserExample example = new UserExample();
        example.createCriteria().andNameLike("ma%").andIdNotEqualTo(-4);
        List<User> list = userMapper.selectByExample(example);
        System.out.println(list);
    }
    @Test
    public void updateByExampleSelective() {
        UserExample example = new UserExample();
        example.createCriteria().andNameLike("ma%").andIdNotEqualTo(-4);
        User user = new User();
        user.setName("mayun-waixingren");
        int count = userMapper.updateByExampleSelective(user, example);
        System.out.println(count);
    }
    @Test
    public void updateByExample() {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(1);
        User user = new User();
        user.setId(1);
        user.setName("avril");
        user.setLabelId(null);
        int count = userMapper.updateByExample(user, example);
        System.out.println(count);
    }
    @Test
    public void updateByPrimaryKeySelective() {
    }
    @Test
    public void updateByPrimaryKey() {
    }
    @Test
    public void selectByExampleByPage() {
    }
    @Test
    public void batchInsert() {
    }
    @Test
    public void batchInsertSelective() {
    }

    public void insertSelectiveSelectKey() {
    }
}
