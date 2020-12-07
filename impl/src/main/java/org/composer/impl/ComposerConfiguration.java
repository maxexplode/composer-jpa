package org.composer.impl;


public class ComposerConfiguration {
    private String catalog;
    private String schemaPatterns;
    private String tableNamePattern;

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchemaPatterns() {
        return schemaPatterns;
    }

    public void setSchemaPatterns(String schemaPatterns) {
        this.schemaPatterns = schemaPatterns;
    }

    public String getTableNamePattern() {
        return tableNamePattern;
    }

    public void setTableNamePattern(String tableNamePattern) {
        this.tableNamePattern = tableNamePattern;
    }
}
