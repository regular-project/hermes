package org.hermes.core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesResourceName);
        }

        properties.load(inputStream);
    }
}
