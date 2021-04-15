package org.hermes.jsonhandler.service;


import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.hermes.core.kafka.HermesConsumer;
import org.hermes.jsonhandler.config.JsonHandlerConfig;
import org.hermes.jsonhandler.extraction.JsonDataExtractor;

import java.util.Properties;

public class JsonHandlerService {

    public void start() throws Exception {
        JsonHandlerConfig jsonHandlerConfig = JsonHandlerConfig.getInstance();

        String topic = initTopic(jsonHandlerConfig);
        Properties properties = initProperties(jsonHandlerConfig);

        HermesConsumer hermesConsumer = new HermesConsumer(properties, topic, new JsonDataExtractor());

        Thread consumerThread = new Thread(hermesConsumer);
        consumerThread.start();
    }

    private String initTopic(JsonHandlerConfig jsonHandlerConfig) {
        return jsonHandlerConfig.graspProperty("application.kafka.topic");
    }

    private Properties initProperties(JsonHandlerConfig jsonHandlerConfig) {
        Properties properties = new Properties();

        String groupId = jsonHandlerConfig.graspProperty("kafka.consumer.group-id");
        String kafkaServerUrl = jsonHandlerConfig.graspProperty("kafka.server.url");
        String kafkaServerPort = jsonHandlerConfig.graspProperty("kafka.server.port");
        String schemaRegistryUrl = jsonHandlerConfig.graspProperty("kafka.schema-registry.url");
        String schemaRegistryPort = jsonHandlerConfig.graspProperty("kafka.schema-registry.port");
        String schemaRegistryFullUrl = schemaRegistryUrl + ":" + schemaRegistryPort;

        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl + ":" + kafkaServerPort);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.setProperty(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryFullUrl);
        properties.setProperty(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

        return properties;
    }
}
