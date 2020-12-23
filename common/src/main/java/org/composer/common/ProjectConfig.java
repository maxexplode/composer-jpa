package org.composer.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectConfig {
    private String name;
    private String packageName;
    private String outPath;
    private String extension;
    private String collectionLoading;
    private char[] escapingDelimiters;
}
