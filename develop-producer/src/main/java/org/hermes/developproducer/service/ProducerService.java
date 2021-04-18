package org.hermes.developproducer.service;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.hermes.core.avro.Field;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.avro.OutputType;
import org.hermes.developproducer.producer.DefaultDevelopProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class ProducerService {

    private final Logger logger = LoggerFactory.getLogger(ProducerService.class.getName());

    public void runProducer() {
        KafkaProducer<String, HermesIngressRecord> producer = DefaultDevelopProducer.getProducer();

        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":{\"street\":"
                + "\"21 2nd Street\",\"city\":\"New York\",\"postalCode\":\"10021-3100\","
                + "\"coordinates\":{\"latitude\":40.7250387,\"longitude\":-73.9932568}}}";
        List<Field> fields = new LinkedList<>();

        Field field = Field.newBuilder().
                setOutputName("name").
                setOutputType(OutputType.SINGLE).
                setSelector("/firstName").
                build();

        fields.add(field);

        HermesIngressRecord hermesRecord = new HermesIngressRecord(json, fields);

        ProducerRecord<String, HermesIngressRecord> producerRecord = new ProducerRecord<>(
                "json-extraction",
                "develop-producer",
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
