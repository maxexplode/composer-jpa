package org.composer.impl.phase;

import org.composer.api.introspect.IntrospectPhase;
import org.composer.common.IntrospectContext;

/**
 * This phase is responsible for initialization introspection
 */
public class InitializationPhase extends IntrospectPhase {

    public InitializationPhase()
    {
        System.out.println("FirstPhase initiated...");
    }

    @Override
    public void handle(IntrospectContext context) {
        context.getTables().forEach(table ->
        {

        });
    }
}
