package org.composer.plugins;

import lombok.Builder;
import lombok.Data;

import java.util.Properties;

@Data
@Builder
public class Context {
    Properties properties;
}
