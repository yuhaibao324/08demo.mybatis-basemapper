package cn.howso.framework.mybatis.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Table;
import org.apache.ibatis.builder.xml.XMLMapperMerger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import cn.howso.framework.mybatis.mapper.xml.BaseMapper;
import cn.howso.framework.mybatis.mapper.xml.ExampleMapper;
import cn.howso.framework.mybatis.mapper.xml.PrimaryKeyMapper;
import cn.howso.framework.mybatis.model.ResMap;

public class XMLMapperMergerImpl implements XMLMapperMerger{

    private InputStream mergeInternal(InputStream inputStream) throws DocumentException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        // conf
        final MapperConf conf = new MapperConf();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(inputStream);
        Element root = doc.getRootElement();
        Attribute attr = root.attribute("namespace");// mapper类名
        String ns = attr.getStringValue();
        Class<?> mapperClazz = Class.forName(ns);
        Table tableAnno = mapperClazz.getAnnotation(Table.class);// 根据注解获取表名
        String tablename = tableAnno.name();
        Element baseResultMap = null;
        List<Element> resultMaps = root.elements("resultMap");
        for(Element e:resultMaps){
            if("BaseResultMap".equals(e.attribute("id").getStringValue())){
                baseResultMap = e;
            }
        }
        if(baseResultMap!=null){
            Element idEle = baseResultMap.element("id");
            if(idEle!=null){
                ResMap idResMap = new ResMap();
                idResMap.setColumn(idEle.attribute("column").getStringValue());
                idResMap.setJdbcType(idEle.attribute("jdbcType").getStringValue());
                idResMap.setProperty(idEle.attribute("property").getStringValue());
                conf.setIdResMap(idResMap);
            }
            List<Element> mappings = baseResultMap.elements();
            List<ResMap> resMaps = mappings.stream().map(x -> {// 从BaseResultMap获取字段映射
                ResMap resMap = new ResMap();
                resMap.setColumn(x.attribute("column").getStringValue());
                resMap.setJdbcType(x.attribute("jdbcType").getStringValue());
                resMap.setProperty(x.attribute("property").getStringValue());
                return resMap;
            }).collect(Collectors.toList());
            conf.setResMaps(resMaps);
        }else{
            throw new RuntimeException("未定义BaseResultMap");
        }
        conf.setNamespace(ns);
        conf.setTablename(tablename);
        // write
        Type[] types = mapperClazz.getGenericInterfaces();
        Map<Type, ParameterizedType> ptMap = new HashMap<>();
        for (Type t : types) {
            if (t instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) t;
                ptMap.put(pt.getRawType(), pt);
            }
        }
        List<Method> methods = new ArrayList<>();// 获取泛型接口的方法
        if (ptMap.get(BaseMapper.class) != null) {
            String idType = ptMap.get(BaseMapper.class).getActualTypeArguments()[2].getTypeName();
            conf.setIdType(idType);
            methods.addAll(Arrays.asList(BaseMapper.class.getMethods()));
        } else {
            if (ptMap.get(ExampleMapper.class) != null) {
                methods.addAll(Arrays.asList(ExampleMapper.class.getMethods()));
            }
            if (ptMap.get(PrimaryKeyMapper.class) != null) {
                methods.addAll(Arrays.asList(PrimaryKeyMapper.class.getMethods()));
                String idType = ptMap.get(PrimaryKeyMapper.class).getActualTypeArguments()[1].getTypeName();
                conf.setIdType(idType);
            }
        }
        Class<XMLMapperSqlProvider> providerClazz = XMLMapperSqlProvider.class;
        XMLMapperSqlProvider provider = null;
        Method[] providerMethods = null;
        provider = providerClazz.newInstance();
        providerMethods = providerClazz.getDeclaredMethods();
        Map<String, Method> providerMethodMap = new HashMap<>();
        for (Method m : providerMethods) {
            providerMethodMap.put(m.getName(), m);
        }
        List<Element> eleList = root.elements();
        Map<String,Element> idEleMap = new HashMap<>();//mapper.xml已经配置的标签
        for(Element ele:eleList){
            String id = ele.attribute("id").getStringValue();
            idEleMap.put(id, ele);
        }
        for (Method m : methods) {//对泛型接口每一个方法，调用provider的同名方法，生成标签，插入到xml。
            if(idEleMap.get(m.getName())==null){//mapper.xml配置的sql优先级高
                Method method = providerMethodMap.get(m.getName());
                String sqlTag = (String) method.invoke(provider, conf);
                root.add(DocumentHelper.parseText(sqlTag).getRootElement());
            }
        }
        byte[] buf = doc.asXML().getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        return in;
    }

    @Test
    public void test() throws DocumentException, IOException, ClassNotFoundException {
        XMLMapperMergerImpl merger = new XMLMapperMergerImpl();
        InputStream in = XMLMapperMergerImpl.class.getResourceAsStream("/book/mapper/UserMapper.xml");
        in = merger.merge(in);
    }

    public InputStream merge(InputStream in) {
        try{
            return mergeInternal(in);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
