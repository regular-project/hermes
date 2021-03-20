package org.hermes.developproducer.model;

import io.confluent.kafka.serializers.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;
import org.hermes.developproducer.config.*;

import java.util.*;

public class Producer {

    private KafkaConfig kafkaConfig;

    public Producer(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    // TODO change map value
    public KafkaProducer<String, String> getProducer() {
        Properties props = new Properties();

        String bootstrapServer = kafkaConfig.graspProperty("kafka.server.url") + ":"
                + kafkaConfig.graspProperty("kafka.server.port");
        String schemaRegistry = kafkaConfig.graspProperty("kafka.schema-registry.url") + ":"
                + kafkaConfig.graspProperty("kafka.schema-registry.port");
        //  Creating Safe Producer
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.setProperty(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        props.setProperty(ProducerConfig.ACKS_CONFIG, "all");

        return new KafkaProducer<>(props);
    }
}
