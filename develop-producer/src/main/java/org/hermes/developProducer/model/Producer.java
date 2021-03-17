package org.hermes.developProducer.model;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.hermes.developProducer.config.KafkaConfig;

import java.util.Properties;

public class Producer {

    public static KafkaProducer<String,String> getProducer() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_SERVER_URL + ":" + KafkaConfig.KAFKA_SERVER_PORT);

        //  Set types of value serializers
        String stringSerializer = StringSerializer.class.getName();
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, stringSerializer);
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, stringSerializer);

        return new KafkaProducer<>(props);
    }
}
