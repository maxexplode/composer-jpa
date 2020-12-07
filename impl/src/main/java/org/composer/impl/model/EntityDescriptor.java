package org.composer.impl.model;

import java.util.Set;

public class EntityDescriptor {
    private String entityName;
    private String tableName;

    Set<ColumnDescriptor> columns;
    Set<ColumnDescriptor> oneToOneColumns;
    Set<ColumnDescriptor> oneToManyColumns;

    private EntityDescriptor superType;

}
