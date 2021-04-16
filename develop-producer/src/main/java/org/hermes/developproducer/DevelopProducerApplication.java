package org.hermes.developproducer;

import org.hermes.developproducer.service.ProducerService;

public class DevelopProducerApplication {

    public static void main(String[] args) {
        new ProducerService().runProducer();
    }
}
