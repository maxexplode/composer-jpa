package org.composer.api.introspect;

import org.composer.common.ComposerRecord;

import java.util.List;

public class IntrospectContext {
    List<ComposerRecord.Table> tables;

    public IntrospectContext(List<ComposerRecord.Table> tables) {
        this.tables = tables;
    }

    public List<ComposerRecord.Table> getTables() {
        return tables;
    }
}
