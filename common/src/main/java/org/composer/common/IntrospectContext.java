package org.composer.common;

import org.composer.common.model.EntityDescriptor;

import java.io.File;
import java.util.*;

public class IntrospectContext {

    List<ComposerRecord.Table> tables;
    List<ComposerRecord.SqlType> sqlTypes;

    private Map<String, ComposerRecord.Table> tableMap = new HashMap<>();
    private Map<String, List<ComposerRecord.Table>> exportedTableMap = new HashMap<>();
    private Map<String, List<ComposerRecord.Table>> importedTableMap = new HashMap<>();

    ThreadLocal<Set<EntityDescriptor>> descriptorLocal = ThreadLocal.withInitial(HashSet::new);
    private ProjectConfig config;

    public IntrospectContext(List<ComposerRecord.Table> tables,List<ComposerRecord.SqlType> sqlTypes, ProjectConfig config) {
        this.tables = tables;
        this.config = config;
        this.sqlTypes = sqlTypes;
        initializeContext();
    }

    private void initializeContext() {
        tables.forEach(table -> tableMap.put(table.name(), table));

        tables.forEach(table -> {
            table.exportedKeys().forEach(importedExportedKey ->
            {
                exportedTableMap.computeIfAbsent(table.name(), name -> new ArrayList<>());
                exportedTableMap.get(table.name()).add(tableMap.get(importedExportedKey.fKTableName()));
            });
        });

        tables.forEach(table -> {
            table.importedKeys().forEach(importedExportedKey ->
            {
                importedTableMap.computeIfAbsent(table.name(), name -> new ArrayList<>());
                importedTableMap.get(table.name()).add(tableMap.get(importedExportedKey.pKTableName()));
            });
        });
    }

    public List<ComposerRecord.Table> getTables() {
        return tables;
    }

    public ThreadLocal<Set<EntityDescriptor>> getDescriptorLocal() {
        return descriptorLocal;
    }

    public ProjectConfig getConfig() {
        return config;
    }

    public String generateOutLocation(String model)
    {
        return config.getOutPath() + File.separator + model + config.getExtension();
    }
}
