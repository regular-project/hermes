package org.hermes.developproducer.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.avro.Enum_type;
import org.avro.Field;
import org.avro.HermesRecord;
import org.hermes.developproducer.config.KafkaConfig;
import org.hermes.developproducer.model.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;


public class KafkaProducerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class.getName());

    public void runProducer() {
        KafkaProducer<String, HermesRecord> producer = Producer.getProducer();

        HermesRecord.Builder hermesBuilder = HermesRecord.newBuilder();
        hermesBuilder.setData("Some big data");
        List<Field> fields = new LinkedList<>();
        fields.add(new Field("name", Enum_type.SINGLE, "name"));
        hermesBuilder.setFields(fields);
        HermesRecord hermesRecord = hermesBuilder.build();

        ProducerRecord<String, HermesRecord> producerRecord = new ProducerRecord<>(KafkaConfig.TOPIC,KafkaConfig.PRODUCER_KEY, hermesRecord);

        producer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                logger.info("Data was successfully sended" + "\n"  +
                        "Topic: " + metadata.topic() + "\n" +
                        "Timestamp: " + metadata.timestamp() + "\n"
                );
            } else {
                exception.printStackTrace();
            }
        });

        producer.flush();
        producer.close();
    }


}
