package org.composer.impl.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.composer.common.IntrospectContext;
import org.apache.velocity.app.VelocityEngine;
import org.composer.common.model.ColumnDescriptor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    private final IntrospectContext introspectContext;

    public Generator(IntrospectContext context) {
        this.introspectContext = context;
    }

    public void init() {
        String props = "C:\\Projects\\composer-jpa\\configuration\\velocity.properties";

        VelocityEngine velocityEngine = new VelocityEngine(props);
        velocityEngine.init();

        List<VelocityContext> contexts = new ArrayList<>();

        ColumnDescriptor.builder().build().getLength();

        introspectContext.getDescriptorLocal().get().forEach(
                entityDescriptor ->
                {
                    VelocityContext context = new VelocityContext();
                    context.put("packageName", introspectContext.getConfig().getPackageName());
                    context.put("identifier", "class");
                    context.put("tableName", entityDescriptor.getTableName());
                    context.put("resourceName", entityDescriptor.getEntityName());
                    context.put("fields", entityDescriptor.getColumns());
                    contexts.add(context);
                }
        );

        Template template = velocityEngine.getTemplate("entity.vm");

        try {
            Files.walk(Paths.get(introspectContext.getConfig().getOutPath()))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }

        contexts.forEach(velocityContext -> {
            try {
                Writer writer = new FileWriter(introspectContext.generateOutLocation((String) velocityContext.internalGet("resourceName")));
                template.merge(velocityContext, writer);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
