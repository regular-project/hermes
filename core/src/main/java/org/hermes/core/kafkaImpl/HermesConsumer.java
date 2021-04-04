package org.hermes.core.kafkaImpl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.hermes.core.avro.HermesRecord;
import org.hermes.core.extraction.DataExtractor;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;


public class HermesConsumer extends KafkaConsumer<String, HermesRecord> implements Runnable {

    private DataExtractor dataExtractor;
    public String topic;

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
                dataExtractor.extract(record);
            }

            this.commitSync();
        }
    }
}
