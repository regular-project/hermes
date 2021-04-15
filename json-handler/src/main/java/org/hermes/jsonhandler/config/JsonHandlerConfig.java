package org.hermes.jsonhandler.config;


import org.hermes.core.config.BaseConfig;

import java.io.IOException;

public final class JsonHandlerConfig extends BaseConfig {

    private static volatile JsonHandlerConfig jsonHandlerConfig;

    private JsonHandlerConfig() {
        super("application.properties", "CONFIG_PATH");
        if (jsonHandlerConfig != null) {
            throw new RuntimeException("User getInstance() method to get the single instance of this class.");
        }
    }

    public static JsonHandlerConfig getInstance() throws IOException {
        if (jsonHandlerConfig == null) {
            synchronized (JsonHandlerConfig.class) {
                if (jsonHandlerConfig == null) {
                    jsonHandlerConfig = new JsonHandlerConfig();
                    jsonHandlerConfig.initProperties();
                }
            }
        }

        return jsonHandlerConfig;
    }
}
