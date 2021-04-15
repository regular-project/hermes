package org.hermes.core.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.extraction.DataExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class HermesConsumer extends KafkaConsumer<String, HermesIngressRecord> implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(HermesConsumer.class.getName());
    static final int RECORD_PERIOD = 1000;

    private final DataExtractor dataExtractor;
    private final String topic;

    public HermesConsumer(Properties properties, String topic, DataExtractor dataExtractor) {
        super(properties);
        this.topic = topic;
        this.dataExtractor = dataExtractor;
    }

    @Override
    public void run() {
        this.subscribe(Collections.singleton(topic));

        while (true) {
            ConsumerRecords<String, HermesIngressRecord> records = this.poll(Duration.ofMillis(RECORD_PERIOD));

            for (ConsumerRecord<String, HermesIngressRecord> record : records) {
                HermesEgressRecord extractionResult = dataExtractor.extract(record);
                logger.info(extractionResult.toString());
            }

            this.commitSync();
        }
    }
}
