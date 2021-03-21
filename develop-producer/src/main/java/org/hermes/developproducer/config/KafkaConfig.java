package org.hermes.developproducer.config;


import org.hermes.core.config.BaseConfig;

import java.io.IOException;

public final class KafkaConfig extends BaseConfig {

    private static volatile KafkaConfig kafkaConfig;

    private KafkaConfig() {
        super("application.properties", "CONFIG_PATH");
        if (kafkaConfig != null) {
            throw new RuntimeException("User getInstance() method to get the single instance of this class.");
        }
    }

    public static KafkaConfig getInstance() throws IOException {
        if (kafkaConfig == null) {
            synchronized (KafkaConfig.class) {
                if (kafkaConfig == null) {
                    kafkaConfig = new KafkaConfig();
                    kafkaConfig.initProperties();
                }
            }
        }

        return kafkaConfig;
    }
}
