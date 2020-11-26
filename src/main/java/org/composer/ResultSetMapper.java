package org.composer;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResultSetMapper {

    public <T> T val(String column, ResultSet rs, Class<T> aClass) {
        try {
            return rs.getObject(column, aClass);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * Catalogs
     *
     * @param metaData
     * @return
     * @throws SQLException
     */
    public List<String> getCatalogs(DatabaseMetaData metaData) throws SQLException {
        List<String> catalogs = new ArrayList<>();
        try (ResultSet rs = metaData.getCatalogs()) {
            while (rs.next()) {
                String catalog = val("TABLE_CAT", rs, String.class);
                catalogs.add(catalog);
            }
        }
        return catalogs;
    }

    /**
     * Catalogs
     *
     * @param metaData
     * @return
     * @throws SQLException
     */
    public List<ComposerRecord.Scheme> getSchemes(DatabaseMetaData metaData) throws SQLException {
        List<ComposerRecord.Scheme> catalogs = new ArrayList<>();
        try (ResultSet rs = metaData.getSchemas()) {
            while (rs.next()) {

                catalogs.add(new ComposerRecord.Scheme(
                        val("TABLE_SCHEM", rs, String.class),
                        val("TABLE_CATALOG", rs, String.class)
                ));
            }
        }
        return catalogs;
    }

    /**
     * Tables
     *
     * @param metaData
     * @param configuration
     * @return
     * @throws SQLException
     */
    public List<ComposerRecord.Table> getTables(DatabaseMetaData metaData, ComposerConfiguration configuration) throws SQLException {
        var tables = new ArrayList<ComposerRecord.Table>();
        try (ResultSet rs = metaData.getTables(configuration.getCatalog(), configuration.getSchemaPatterns(), configuration.getTableNamePattern(), null)) {
            while (rs.next()) {
                String tableName = val("TABLE_NAME", rs, String.class);
                ComposerRecord.Table table = new ComposerRecord.Table(
                        val("TABLE_CAT", rs, String.class),
                        val("TABLE_SCHEM", rs, String.class),
                        tableName,
                        Optional.ofNullable(val("TABLE_TYPE", rs, String.class)).map(s ->
                        {
                            switch (s) {
                                case "SYSTEM TABLE" -> {
                                    return ComposerRecord.TableType.SYSTEM_TABLE;
                                }
                                case "GLOBAL TEMPORARY" -> {
                                    return ComposerRecord.TableType.GLOBAL_TEMPORARY;
                                }
                                case "LOCAL TEMPORARY" -> {
                                    return ComposerRecord.TableType.LOCAL_TEMPORARY;
                                }
                                case "SYSTEM INDEX" -> {
                                    return ComposerRecord.TableType.SYSTEM_INDEX;
                                }
                                case "SYSTEM TOAST INDEX" -> {
                                    return ComposerRecord.TableType.SYSTEM_TOAST_INDEX;
                                }
                                case "SYSTEM VIEW", "VIEW" -> {
                                    return ComposerRecord.TableType.VIEW;
                                }
                                default -> {
                                    return ComposerRecord.TableType.valueOf(s);
                                }

                            }
                        }).orElse(null),
                        val("REMARKS", rs, String.class),
                        val("TYPE_CAT", rs, String.class),
                        val("TYPE_SCHEM", rs, String.class),
                        val("TYPE_NAME", rs, String.class),
                        val("SELF_REFERENCING_COL_NAME", rs, String.class),
                        Optional.ofNullable(val("REF_GENERATION", rs, String.class)).filter(s -> s.length() > 0).map(ComposerRecord.RefGeneration::valueOf).orElse(null),
                        getColumns(metaData, configuration, tableName),
                        getPrimaryKeys(metaData, configuration, tableName),
                        getImportedExportedKeys(false, metaData, configuration, tableName),
                        getImportedExportedKeys(true, metaData, configuration, tableName)
                );
                tables.add(table);
            }
        }
        return tables;
    }

    public List<ComposerRecord.ImportedExportedKey> getImportedExportedKeys(boolean exported, DatabaseMetaData metaData, ComposerConfiguration configuration, String tableName) throws SQLException {
        List<ComposerRecord.ImportedExportedKey> keys = new ArrayList<>();
        if (exported) {
            try (ResultSet rs = metaData.getExportedKeys(configuration.getCatalog(), configuration.getSchemaPatterns(), tableName)) {
                while (rs.next()) {
                    keys.add(mapImportedExportedKey(rs));
                }
            }
        } else {
            try (ResultSet rs = metaData.getImportedKeys(configuration.getCatalog(), configuration.getSchemaPatterns(), tableName)) {
                while (rs.next()) {
                    keys.add(mapImportedExportedKey(rs));
                }
            }
        }
        return keys;
    }

    private ComposerRecord.ImportedExportedKey mapImportedExportedKey(ResultSet rs) throws SQLException {
        return new ComposerRecord.ImportedExportedKey(
                val("PKTABLE_CAT", rs, String.class),
                val("PKTABLE_SCHEM", rs, String.class),
                val("PKTABLE_NAME", rs, String.class),
                val("PKCOLUMN_NAME", rs, String.class),
                val("FKTABLE_CAT", rs, String.class),
                val("FKTABLE_SCHEM", rs, String.class),
                val("FKTABLE_NAME", rs, String.class),
                val("FKCOLUMN_NAME", rs, String.class),
                val("KEY_SEQ", rs, Short.class),
                mapUpdateDeleteRule(val("UPDATE_RULE", rs, Short.class)),
                mapUpdateDeleteRule(val("DELETE_RULE", rs, Short.class)),
                val("FK_NAME", rs, String.class),
                val("PK_NAME", rs, String.class),
                mapDeferrability(val("DEFERRABILITY", rs, Short.class))
        );
    }

    private ComposerRecord.UpdateDeleteRule mapUpdateDeleteRule(Short rule) {
        return Optional.ofNullable(rule).map(i ->
        {
            switch (i) {
                case 1 -> {
                    return ComposerRecord.UpdateDeleteRule.IMPORTED_KEY_CASCADE;
                }
                case 2 -> {
                    return ComposerRecord.UpdateDeleteRule.IMPORTED_KEY_SET_NULL;
                }
                case 3 -> {
                    return ComposerRecord.UpdateDeleteRule.IMPORTED_KEY_SET_DEFAULT;
                }
                case 4 -> {
                    return ComposerRecord.UpdateDeleteRule.IMPORTED_KEY_RESTRICT;
                }
                default -> {
                    return ComposerRecord.UpdateDeleteRule.IMPORTED_NO_ACTION;
                }
            }
        }).orElse(ComposerRecord.UpdateDeleteRule.IMPORTED_NO_ACTION);
    }

    private ComposerRecord.Deferrability mapDeferrability(Short deferrability) {
        return Optional.ofNullable(deferrability).map(i ->
        {
            switch (i) {
                case 1 -> {
                    return ComposerRecord.Deferrability.IMPORTED_KEY_INITIALLY_IMMEDIATE;
                }
                case 2 -> {
                    return ComposerRecord.Deferrability.IMPORTED_KEY_NOT_DEFERRABLE;
                }
                default -> {
                    return ComposerRecord.Deferrability.IMPORTED_KEY_INITIALLY_DEFERRED;
                }
            }
        }).orElse(ComposerRecord.Deferrability.IMPORTED_KEY_INITIALLY_DEFERRED);
    }

    public List<ComposerRecord.PrimaryKey> getPrimaryKeys(DatabaseMetaData metaData, ComposerConfiguration configuration, String tableName) throws SQLException {
        List<ComposerRecord.PrimaryKey> primaryKeys = new ArrayList<>();
        try (ResultSet rs = metaData.getPrimaryKeys(configuration.getCatalog(), configuration.getSchemaPatterns(), tableName)) {
            while (rs.next()) {
                ComposerRecord.PrimaryKey primaryKey = new ComposerRecord.PrimaryKey(
                        val("TABLE_CAT", rs, String.class),
                        val("TABLE_SCHEM", rs, String.class),
                        val("TABLE_NAME", rs, String.class),
                        val("COLUMN_NAME", rs, String.class),
                        val("KEY_SEQ", rs, Short.class),
                        val("PK_NAME", rs, String.class)
                );
                primaryKeys.add(primaryKey);
            }
        }
        return primaryKeys;
    }

    public List<ComposerRecord.Column> getColumns(DatabaseMetaData metaData, ComposerConfiguration configuration, String tableName) throws SQLException {
        List<ComposerRecord.Column> columns = new ArrayList<>();
        try (ResultSet rs = metaData.getColumns(configuration.getCatalog(), configuration.getSchemaPatterns(), tableName, null)) {
            while (rs.next()) {
                ComposerRecord.Column column = new ComposerRecord.Column(
                        val("TABLE_CAT", rs, String.class),
                        val("TABLE_SCHEM", rs, String.class),
                        val("TABLE_NAME", rs, String.class),
                        val("COLUMN_NAME", rs, String.class),
                        val("DATA_TYPE", rs, Integer.class),
                        val("TYPE_NAME", rs, String.class),
                        val("COLUMN_SIZE", rs, Integer.class),
                        val("BUFFER_LENGTH", rs, String.class),
                        val("DECIMAL_DIGITS", rs, Integer.class),
                        val("NUM_PREC_RADIX", rs, Integer.class),
                        Optional.ofNullable(val("NULLABLE", null, Integer.class)).map(i ->
                        {
                            switch (i) {
                                case 1 -> {
                                    return ComposerRecord.ColumnNullable.NO_NULL;
                                }
                                case 0 -> {
                                    return ComposerRecord.ColumnNullable.NULLABLE;
                                }
                                default -> {
                                    return ComposerRecord.ColumnNullable.UNKNOWN;
                                }
                            }
                        }).orElse(null),
                        val("REMARKS", rs, String.class),
                        val("COLUMN_DEF", rs, String.class),
                        val("SQL_DATA_TYPE", rs, Integer.class),
                        val("SQL_DATETIME_SUB", rs, Integer.class),
                        val("CHAR_OCTET_LENGTH", rs, Integer.class),
                        val("ORDINAL_POSITION", rs, Integer.class),
                        map(val("IS_NULLABLE", rs, String.class)),
                        val("SCOPE_CATALOG", rs, String.class),
                        val("SCOPE_SCHEMA", rs, String.class),
                        val("SCOPE_TABLE", rs, String.class),
                        val("SOURCE_DATA_TYPE", rs, Short.class),
                        map(val("IS_AUTOINCREMENT", rs, String.class)),
                        map(val("IS_GENERATEDCOLUMN", rs, String.class))
                );
                columns.add(column);
            }
        }
        return columns;
    }

    private ComposerRecord.BooleanResponse map(String code) {
        return Optional.ofNullable(code).map(s ->
        {
            switch (s) {
                case "YES" -> {
                    return ComposerRecord.BooleanResponse.YES;
                }
                case "NO" -> {
                    return ComposerRecord.BooleanResponse.NO;
                }
                default -> {
                    return ComposerRecord.BooleanResponse.NA;
                }
            }
        }).orElse(null);

    }

}
