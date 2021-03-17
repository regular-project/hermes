package org.hermes.developProducer.service;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.hermes.developProducer.config.KafkaConfig;
import org.hermes.developProducer.model.Producer;

import java.util.logging.Logger;


public class KafkaProducerService {

    private final Logger logger = Logger.getLogger(KafkaProducerService.class.getName());

    public void runProducer() {
        KafkaProducer<String, String> producer = Producer.getProducer();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(KafkaConfig.TOPIC, "hello world");

        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                logger.info("Data was successfully sended" + "\n"  +
                        "Topic: " + metadata.topic() + "\n" +
                        "Timestamp: " + metadata.timestamp() + "\n"
                        );
            }
        });

        producer.flush();
        producer.close();
    }


}
