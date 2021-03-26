package org.hermes.developproducer;

import org.hermes.developproducer.service.ProduceService;

public class DevelopProducerApplication {

    public static void main(String[] args) throws Exception {
        ProduceService produceService = new ProduceService();
        produceService.runProducer();
    }
}
