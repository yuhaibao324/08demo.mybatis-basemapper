package cn.howso.framework.mybatis.model;

import com.google.gson.Gson;

public class ResMap {
    String column;
    String property;
    String jdbcType;
    
    public String getColumn() {
        return column;
    }
    
    public void setColumn(String column) {
        this.column = column;
    }
    
    public String getProperty() {
        return property;
    }
    
    public void setProperty(String property) {
        this.property = property;
    }
    
    public String getJdbcType() {
        return jdbcType;
    }
    
    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
