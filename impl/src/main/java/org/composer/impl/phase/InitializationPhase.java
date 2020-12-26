package org.composer.impl.phase;

import org.apache.commons.text.CaseUtils;
import org.composer.api.introspect.IntrospectPhase;
import org.composer.common.IntrospectContext;
import org.composer.common.model.ColumnDescriptor;
import org.composer.common.model.EntityDescriptor;

import java.util.stream.Collectors;

/**
 * This phase is responsible for initialization introspection
 */
public class InitializationPhase extends IntrospectPhase {

    public InitializationPhase() {
        System.out.println("FirstPhase initiated...");
    }

    @Override
    public void handle(IntrospectContext context) {
        context.getTables().forEach(table ->
        {
            context.getDescriptorLocal().get().add(EntityDescriptor.builder()
                    .entityName(CaseUtils.toCamelCase(table.name(), true, context.getConfig().getEscapingDelimiters()))
                    .tableName(table.name())
                    .columns(table.columns().stream().map(column ->
                            ColumnDescriptor.builder()
                                    .columnName(column.columnName())
                                    .type(column.typeName())
                                    .build()).collect(Collectors.toSet()))
                    .build());
        });
    }
}
