package org.composer.impl.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.composer.common.IntrospectContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.StringWriter;

public class Generator {

    private final IntrospectContext context;


    public Generator(IntrospectContext context) {
        this.context = context;
    }

    public void init()
    {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template t = velocityEngine.getTemplate("index.vm");

        VelocityContext context = new VelocityContext();
        context.put("name", "World");

        StringWriter writer = new StringWriter();
        t.merge( context, writer );
    }
}
