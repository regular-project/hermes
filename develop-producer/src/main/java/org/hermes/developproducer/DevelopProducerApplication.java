package org.hermes.developproducer;

import org.hermes.developproducer.service.KafkaProducerService;

import java.io.*;

public class DevelopProducerApplication {

    public static void main(String[] args) {
        KafkaProducerService kafkaProducerService = new KafkaProducerService();
        kafkaProducerService.runProducer();
    }
}
