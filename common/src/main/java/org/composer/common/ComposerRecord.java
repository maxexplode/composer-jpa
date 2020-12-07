package org.composer.common;

import java.util.List;

public class ComposerRecord {

    public enum TableType {
        VIEW, SYSTEM_TABLE, GLOBAL_TEMPORARY, LOCAL_TEMPORARY, ALIAS, SYNONYM,
        /* POSTGRE SQL */
        SYSTEM_INDEX,
        SYSTEM_TOAST_INDEX,
        /* Oracle */
        TABLE
    }

    public enum RefGeneration {SYSTEM, USER, DERIVED}

    public enum ColumnNullable {NO_NULL, NULLABLE, UNKNOWN}

    public enum BooleanResponse {YES, NO, NA}

    public enum UpdateDeleteRule {IMPORTED_NO_ACTION, IMPORTED_KEY_CASCADE, IMPORTED_KEY_SET_NULL, IMPORTED_KEY_SET_DEFAULT, IMPORTED_KEY_RESTRICT}

    public enum Deferrability {IMPORTED_KEY_INITIALLY_DEFERRED, IMPORTED_KEY_INITIALLY_IMMEDIATE, IMPORTED_KEY_NOT_DEFERRABLE}

    public static record Scheme(String scheme, String catalog) {

    }

    ;

    public static record ColumnPrivileges(

    ) {

    }

    public static record ImportedExportedKey(
            String pKTableCatalog,
            String pKTableScheme,
            String pKTableName,
            String pKColumnName,
            String fKTableCatalog,
            String fKTableScheme,
            String fKTableName,
            String fKColumnName,
            Short keySequence,
            UpdateDeleteRule updateRule,
            UpdateDeleteRule deleteRule,
            String fkName,
            String pkName,
            Deferrability deferrability
    ) {
    }

    public static record PrimaryKey(String tableCatalog,
                                    String tableScheme,
                                    String tableName,
                                    String columnName,
                                    Short keySequence,
                                    String primaryKey
    ) {
    }

    public static record Column(
            String tableCatalog,
            String tableScheme,
            String tableName,
            String columnName,
            Integer dataType,
            String typeName,
            Integer columnSize,
            String bufferLength,
            Integer decimalDigits,
            Integer radix,
            ColumnNullable nullable,
            String remarks,
            String columnDefinition,
            Integer sqlDataType,
            Integer sqlDateTimeSub,
            Integer charOctetLength,
            Integer ordinalPosition,
            BooleanResponse isoNullable,
            String scopeCatalog,
            String scopeSchema,
            String scopeTable,
            Short sourceDataType,
            BooleanResponse isAutoIncrement,
            BooleanResponse isGeneratedColumn
    ) {
    }

    public static record Table(
            String catalog,
            String schema,
            String name,
            TableType type,
            String remarks,
            String typeCat,
            String typeScheme,
            String typeName,
            String selfReferencingColName,
            RefGeneration refGeneration,
            List<Column> columns,
            List<PrimaryKey> primaryKeys,
            List<ImportedExportedKey> importedKeys,
            List<ImportedExportedKey> exportedKeys
    ) {
    }


}
