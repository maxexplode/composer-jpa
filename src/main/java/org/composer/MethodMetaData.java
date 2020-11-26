package org.composer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodMetaData {

    private final Map<String, List<String>> metaData = new HashMap<>() {{
        put("java.sql.DatabaseMetaData.getTables", List.of("TABLE_TYPE"));
    }};
}
