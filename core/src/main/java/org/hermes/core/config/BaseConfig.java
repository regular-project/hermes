package org.hermes.core.config;

import java.io.*;
import java.util.*;

public class BaseConfig {

    private final Properties properties = new Properties();

    private final String propertiesResourceName;
    private final String configPathEnv;

    protected BaseConfig(String propertiesResourceName, String configPathEnv) {
        this.propertiesResourceName = propertiesResourceName;
        this.configPathEnv = configPathEnv;
    }

    public String graspProperty(String key) {
        return properties.getProperty(key);
    }

    protected void initProperties() throws IOException {
        InputStream inputStream;
        String configPath = System.getenv(configPathEnv);

        if (configPath != null) {
            inputStream = new FileInputStream(configPath);
        } else {
            inputStream = getClass().getResourceAsStream(propertiesResourceName);
        }

        properties.load(inputStream);
    }
}
