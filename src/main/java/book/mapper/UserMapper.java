package book.mapper;

import book.model.User;
import book.model.UserExample;
import book.sqlprovider.Table;
@Table(name="sys_user")
public interface UserMapper extends BaseMapper<User,UserExample,Integer>{
}