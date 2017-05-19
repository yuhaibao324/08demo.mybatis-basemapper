package cn.howso.framework.mybatis.provider;

import java.util.Map;

public class RawSqlProvider {
    public String insertSelectiveSelectKey(Map<String,Object> param){
        return "insert into sys_user(id,name,label_id)values("+param.get("id")+",#{record.name},#{record.labelId})";
    }
}
