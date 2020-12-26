package org.composer.config;

import org.composer.common.AppConstants;

import java.io.File;

public class ConfigUtil {

    public static final String COMPOSER_HOME = System.getProperty(AppConstants.COMPOSER_HOME);

    public static String composerConfig() {
        return COMPOSER_HOME + AppConstants.CONFIGURATION + File.separator + AppConstants.FILE_CONFIG;
    }

    public static String velocityConfig() {
        return COMPOSER_HOME + AppConstants.CONFIGURATION + File.separator + AppConstants.VELOCITY_CONFIG;
    }
}
