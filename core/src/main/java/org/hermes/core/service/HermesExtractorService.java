package org.hermes.core.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.extraction.DataExtractor;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class HermesExtractorService {

    private final DataExtractor dataExtractor;

    public HermesExtractorService(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;
    }

    public HermesEgressRecord extract(ConsumerRecord<String, HermesIngressRecord> record)  {
        HermesEgressRecord extractionResult = null;

        try {
            HermesIngressRecord hermesRecord = record.value();
            extractionResult = dataExtractor.extract(hermesRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return extractionResult;
    }
}
