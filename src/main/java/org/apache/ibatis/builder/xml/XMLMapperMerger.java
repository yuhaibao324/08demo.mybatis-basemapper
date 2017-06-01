package org.apache.ibatis.builder.xml;

import java.io.InputStream;

public interface XMLMapperMerger {
    public InputStream merge(InputStream in);
}
