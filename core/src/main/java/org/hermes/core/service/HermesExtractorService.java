package org.hermes.core.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.extraction.DataExtractor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HermesExtractorService {

    private final DataExtractor dataExtractor;

    public HermesExtractorService(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;
    }

    public HermesEgressRecord extract(ConsumerRecord<String, SpecificRecord> record)  {
        HermesEgressRecord extractionResult = null;

        try {
            SpecificRecord hermesRecord = record.value();
            extractionResult = dataExtractor.extract(hermesRecord);
        } catch (Exception e) {
            log.error("Error occurred in {} during extracting the data from the record with offset: {}",
                    dataExtractor.getClass(),
                    record.offset(),
                    e);
        }


        return extractionResult;
    }
}
