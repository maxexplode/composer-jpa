package org.composer.plugins;

public interface Plugin {

    void initialize(Context context);

    void start();

    void stop();
}
