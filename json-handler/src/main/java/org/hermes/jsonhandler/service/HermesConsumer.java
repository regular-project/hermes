package org.hermes.jsonhandler.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.extraction.DataExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class HermesConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HermesConsumer.class);

    private final DataExtractor dataExtractor;

    public HermesConsumer(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;
    }

    @KafkaListener(topics = "${hermes.kafka.consumer.topic}")
    public void consume(ConsumerRecord<String, HermesIngressRecord> records) {
        HermesEgressRecord hermesEgressRecord = dataExtractor.extract(records);
        LOGGER.info("Extraction result --> {}", hermesEgressRecord);
    }
}
