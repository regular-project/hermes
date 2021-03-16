package org.hermes.developProducer.service;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.hermes.developProducer.config.KafkaProducerConfig;

import java.util.logging.Logger;


public class KafkaProducerService {

    private final Logger logger = Logger.getLogger(KafkaProducerService.class.getName());

    public void sendMessageFromProducer() {
//        var producerProperties = KafkaProducerConfig.getKafkaProducerProperties();
//        var kafkaProducer = new KafkaProducer<String,String>(producerProperties);
//        var producerRecord = new ProducerRecord<String, String>("first-topic", "hello world");
//
//        kafkaProducer.send(producerRecord, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                logger.info("Data was successfully sended" + "\n"  +
//                        "Topic: " + metadata.topic() + "\n" +
//                        "Timestamp: " + metadata.timestamp() + "\n"
//                        );
//            }
//        });
//
//        kafkaProducer.flush();
//        kafkaProducer.close();
    }


}
