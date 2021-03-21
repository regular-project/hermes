package org.hermes.developproducer.producer;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.avro.HermesRecord;
import org.hermes.developproducer.config.KafkaConfig;

import java.util.Properties;

public final class DefaultDevelopProducer {

    public static KafkaProducer<String, HermesRecord> getProducer() throws Exception {
        Properties props = new Properties();
        KafkaConfig config = KafkaConfig.getInstance();

        String kafkaServerUrl = config.graspProperty("kafka.server.url");
        String kafkaServerPort = config.graspProperty("kafka.server.port");
        String schemaRegistryUrl = config.graspProperty("kafka.schema-registry.url");
        String schemaRegistryPort = config.graspProperty("kafka.schema-registry.port");
        String schemaRegistryFullUrl = schemaRegistryUrl + ":" + schemaRegistryPort;

        //  Creating Safe Producer
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl + ":" + kafkaServerPort);
        props.setProperty(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryFullUrl);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        props.setProperty(ProducerConfig.ACKS_CONFIG, "all");

        return new KafkaProducer<>(props);
    }
}
