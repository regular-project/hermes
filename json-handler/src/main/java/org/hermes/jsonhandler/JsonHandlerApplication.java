package org.hermes.jsonhandler;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDe;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.hermes.core.extraction.DataExtractor;
import org.hermes.core.kafkaImpl.HermesConsumer;
import org.hermes.jsonhandler.config.KafkaConfig;
import org.hermes.jsonhandler.extraction.JsonDataExtractor;

import java.util.Properties;

public class JsonHandlerApplication {

    public static void main(String[] args) throws Exception {
        KafkaConfig config = KafkaConfig.getInstance();
        Properties properties = new Properties();

        String groupId = config.graspProperty("kafka.consumer.group-id");
        String kafkaServerUrl = config.graspProperty("kafka.server.url");
        String kafkaServerPort = config.graspProperty("kafka.server.port");
        String schemaRegistryUrl = config.graspProperty("kafka.schema-registry.url");
        String schemaRegistryPort = config.graspProperty("kafka.schema-registry.port");
        String schemaRegistryFullUrl = schemaRegistryUrl + ":" + schemaRegistryPort;

        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl + ":" + kafkaServerPort);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.setProperty(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryFullUrl);
        properties.setProperty(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,"true");

        DataExtractor dataExtractor = new JsonDataExtractor();

        HermesConsumer hermesConsumer = new HermesConsumer(properties,"develop-producer", dataExtractor);

        Thread consumerThread = new Thread(hermesConsumer);
        consumerThread.start();
    }
}
