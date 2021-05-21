package org.hermes.developproducer.service;

import org.hermes.core.avro.Field;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.avro.OutputTopic;
import org.hermes.core.avro.OutputType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class HermesDevelopProducerService {

    @Value("${hermes.kafka.producer.topic}")
    private String topic;

    @Value("${hermes.kafka.producer.key}")
    private String producerKey;

    private final KafkaTemplate<String, HermesIngressRecord> kafkaTemplate;

    public HermesDevelopProducerService(KafkaTemplate<String, HermesIngressRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    private void sendMessage() {
        String json = "{\"firstName\":\"John\","
                + "\"cars\":[\"Ford\",\"BMW\",\"Fiat\"],"
                + "\"lastName\":\"Doe\",\"address\":{\"street\":"
                + "\"21 2nd Street\",\"city\":\"New York\",\"postalCode\":\"10021-3100\","
                + "\"coordinates\":{\"latitude\":40.7250387,\"longitude\":-73.9932568}}}";

        List<Field> fieldList = new LinkedList<>();
        Field field = new Field("/cars", OutputType.SINGLE, OutputTopic.FINAL, "name");
        fieldList.add(field);

        HermesIngressRecord hermesIngressRecord = HermesIngressRecord.newBuilder()
                .setData(json)
                .setFields(fieldList)
                .build();

        kafkaTemplate.send(topic, producerKey, hermesIngressRecord);

        ListenableFuture<SendResult<String, HermesIngressRecord>> future =
                kafkaTemplate.send(topic, producerKey, hermesIngressRecord);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, HermesIngressRecord> result) {
                System.out.println("Sent message with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message due to : " + ex.getMessage());
            }
        });
    }
}
