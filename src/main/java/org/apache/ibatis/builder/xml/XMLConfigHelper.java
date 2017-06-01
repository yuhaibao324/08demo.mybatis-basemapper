package org.apache.ibatis.builder.xml;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLConfigHelper {
    private Document doc;
    {
        init();
    }
    public void init(){
        String resource = "mybatis-basemapper.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            doc = reader.read(inputStream);
        } catch (Exception e) {
            e.printStackTrace();//采用默认行为
        }
    }
    public String getXmlMapperMerger(){
        if(doc==null){
            return "org.apache.ibatis.builder.xml.DefaultXMLMapperMerger";//提供默认值
        }
        List<Element> elements = doc.getRootElement().elements();
        for(Element ele:elements){
            if("xmlMapperMerger".equals(ele.attributeValue("id"))){
                return ele.attributeValue("class");
            }
        }
        throw new RuntimeException("xmlMapperMerger not found");
    }
}
