package org.composer.common;

import org.composer.common.model.EntityDescriptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntrospectContext {
    List<ComposerRecord.Table> tables;

    ThreadLocal<Set<EntityDescriptor>> descriptorLocal = ThreadLocal.withInitial(HashSet::new);

    public IntrospectContext(List<ComposerRecord.Table> tables) {
        this.tables = tables;
    }

    public List<ComposerRecord.Table> getTables() {
        return tables;
    }
}
