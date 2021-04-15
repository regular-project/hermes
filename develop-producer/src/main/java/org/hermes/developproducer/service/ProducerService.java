package org.hermes.developproducer.service;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.hermes.core.avro.EnumType;
import org.hermes.core.avro.Field;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.developproducer.config.KafkaConfig;
import org.hermes.developproducer.producer.DefaultDevelopProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class ProducerService {

    private final Logger logger = LoggerFactory.getLogger(ProducerService.class.getName());

    public void runProducer() throws Exception {
        KafkaProducer<String, HermesIngressRecord> producer = DefaultDevelopProducer.getProducer();
        KafkaConfig config = KafkaConfig.getInstance();

//        HermesRecord.Builder hermesBuilder = HermesRecord.newBuilder();
//        hermesBuilder.setData("Some big data");
//        List<Field> fields = new LinkedList<>();
//        fields.add(new Field("name", EnumType.SINGLE, "name"));
//        hermesBuilder.setFields(fields);
//        HermesRecord hermesRecord = hermesBuilder.build();

        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":{\"street\":"
                + "\"21 2nd Street\",\"city\":\"New York\",\"postalCode\":\"10021-3100\","
                + "\"coordinates\":{\"latitude\":40.7250387,\"longitude\":-73.9932568}}}";
        List<Field> fields = new LinkedList<>();
        fields.add(new Field("/firstName", EnumType.SINGLE, "name"));
        HermesIngressRecord hermesRecord = new HermesIngressRecord(json, fields);

        String topic = config.graspProperty("kafka.producer.topic");
        String producerKey = config.graspProperty("kafka.producer.key");
        ProducerRecord<String, HermesIngressRecord> producerRecord = new ProducerRecord<>(
                topic,
                producerKey,
                hermesRecord
        );

        producer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                logger.info("Data was successfully sended"
                        + "\n"
                        + "Topic: "
                        + metadata.topic()
                        + "\n"
                        + "Timestamp: "
                        + metadata.timestamp()
                        + "\n"
                );
            } else {
                exception.printStackTrace();
            }
        });

        producer.flush();
    }


}
