package org.hermes.developproducer.model;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.hermes.developproducer.config.KafkaConfig;

import java.util.Properties;

public class Producer {

//    public static KafkaProducer<String, HermesRecord> getProducer() {
//        Properties props = new Properties();
//
//        //  Creating Safe Producer
//        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_SERVER_URL + ":" + KafkaConfig.KAFKA_SERVER_PORT);
//        props.setProperty(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, KafkaConfig.SCHEMA_REGISTRY_URL + ":" + KafkaConfig.SCHEMA_REGISTRY_PORT);
//        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
//        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
//        props.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
//        props.setProperty(ProducerConfig.ACKS_CONFIG, "all");
//
//        return new KafkaProducer<>(props);
//    }
}
