package org.hermes.core.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.extraction.DataExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HermesExtractorService {

    private final DataExtractor dataExtractor;

    private final static Logger log = LoggerFactory.getLogger(HermesExtractorService.class);

    public HermesExtractorService(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;
    }

    public HermesEgressRecord extract(ConsumerRecord<String, HermesIngressRecord> record)  {
        HermesEgressRecord extractionResult = null;

        try {
            HermesIngressRecord hermesRecord = record.value();
            extractionResult = dataExtractor.extract(hermesRecord);
        } catch (IOException e) {
            log.error("Error occurred in {} during extracting the data.", dataExtractor.getClass().toString(), e.getCause());
        }


        return extractionResult;
    }
}
