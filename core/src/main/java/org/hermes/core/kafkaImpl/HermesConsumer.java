package org.hermes.core.kafkaImpl;

import org.apache.kafka.clients.consumer.*;
import org.hermes.core.avro.*;
import org.hermes.core.extraction.*;
import org.slf4j.*;

import java.time.*;
import java.util.*;


public class HermesConsumer extends KafkaConsumer<String, HermesRecord> implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(HermesConsumer.class.getName());

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
            ConsumerRecords<String, HermesRecord> records = this.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, HermesRecord> record: records) {
                ExtractionResult extractionResult = dataExtractor.extract(record);
                logger.info(String.valueOf(extractionResult));
            }

            this.commitSync();
        }
    }
}
