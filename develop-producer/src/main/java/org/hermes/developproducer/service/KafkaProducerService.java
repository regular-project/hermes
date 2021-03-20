package org.hermes.developproducer.service;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.hermes.developproducer.config.KafkaConfig;
import org.hermes.developproducer.model.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KafkaProducerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class.getName());

    public void runProducer() {
        
//        KafkaProducer<String, HermesRecord> producer = Producer.getProducer();
//
//        HermesRecord.Builder hermesBuilder = HermesRecord.newBuilder();
//        hermesBuilder.setData("Some big data");
//        HermesRecord hermesRecord = hermesBuilder.build();
//
//        ProducerRecord<String, HermesRecord> producerRecord = new ProducerRecord<>(KafkaConfig.TOPIC,KafkaConfig.PRODUCER_KEY, hermesRecord);
//
//        producer.send(producerRecord, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                if (exception == null) {
//                    logger.info("Data was successfully sended" + "\n"  +
//                            "Topic: " + metadata.topic() + "\n" +
//                            "Timestamp: " + metadata.timestamp() + "\n"
//                    );
//                } else {
//                    exception.printStackTrace();
//                }
//
//            }
//        });

//        producer.flush();
//        producer.close();
    }


}
