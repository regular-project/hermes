package org.hermes.developproducer.config;

import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class KafkaConfigTest {

    @Test
    public void testKafkaConfig() throws Exception{
        assertNotNull(KafkaConfig.getInstance());

        KafkaConfig kafkaConfig = KafkaConfig.getInstance();
        assertEquals("127.0.0.1", kafkaConfig.graspProperty("kafka.server.url"));
        assertEquals("9092", kafkaConfig.graspProperty("kafka.server.port"));
        assertEquals("develop-producer",kafkaConfig.graspProperty("kafka.producer.topic"));
    }
}
