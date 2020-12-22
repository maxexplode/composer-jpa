package org.composer.impl.phase;

import org.composer.api.introspect.IntrospectPhase;
import org.composer.common.IntrospectContext;

/**
 * This phase is responsible for identify entities
 */
public class IdentifierPhase extends IntrospectPhase {

    public IdentifierPhase()
    {
        System.out.println("SecondPhase initiated...");
    }

    @Override
    public void handle(IntrospectContext context) {

    }
}
