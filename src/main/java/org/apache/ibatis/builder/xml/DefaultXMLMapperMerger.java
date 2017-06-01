package org.apache.ibatis.builder.xml;

import java.io.InputStream;

public class DefaultXMLMapperMerger implements XMLMapperMerger {
    public InputStream merge(InputStream in){
        return in;
    }
}
