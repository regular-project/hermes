package org.hermes.schemainitializer.service;

import org.apache.avro.specific.SpecificRecord;
import org.hermes.schemainitializer.initializer.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HermesProducerService {

    @Value("${hermes.kafka.producer.key}")
    private String producerKey;

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    private final List<Provider> providers;

    public HermesProducerService(KafkaTemplate<String, SpecificRecord> kafkaTemplate, List<Provider> providers) {
        this.kafkaTemplate = kafkaTemplate;
        this.providers = providers;
    }

    @PostConstruct
    public void sendMessage() {
        providers.forEach(provider -> kafkaTemplate.send(provider.getTopic(), producerKey, provider.getRecord())
                .addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, SpecificRecord> result) {
                System.out.println("Sent message with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message due to : " + ex.getMessage());
            }
        }));
    }

}
