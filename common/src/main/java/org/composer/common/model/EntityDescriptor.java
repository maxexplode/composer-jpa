package org.composer.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class EntityDescriptor {
    private String entityName;
    private String tableName;
    
    Set<ColumnDescriptor> columns;
    Set<ColumnDescriptor> oneToOneColumns;
    Set<ColumnDescriptor> oneToManyColumns;

    private EntityDescriptor superType;

}
