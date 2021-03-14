package org.hermes.developProducer;

import org.hermes.developProducer.service.KafkaProducerService;

public class DevelopProducerApplication {

    public static void main(String[] args) {
        var kafkaProducerService = new KafkaProducerService();
        kafkaProducerService.sendMessageFromProducer();
    }
}
