package org.composer.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnDescriptor {
    private String columnName;
    private String columnDescription;
    private String type;
    private boolean nullable;
    private int length;
    private Cardinality cardinality;
}
