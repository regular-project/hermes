package org.hermes.jsonhandler.extraction;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.apache.kafka.clients.consumer.*;
import org.hermes.core.avro.*;
import org.hermes.core.extraction.*;

import java.util.*;

public class JsonDataExtractor implements DataExtractor {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ExtractionResult extract(ConsumerRecord<String, HermesRecord> record) {
        ExtractionResult extractionResult = null;

        try {
            HermesRecord hermesRecord = record.value();
            extractionResult = extractJson(hermesRecord);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return extractionResult;
    }

    private ExtractionResult extractJson(HermesRecord record) throws JsonProcessingException {
        List<ExtractionField> extractionFieldList = new ArrayList<>(record.getFields().size());
        JsonNode node = mapper.readTree(record.getData());

        for (Field field : record.getFields()) {
            JsonNode selectedValue = node.at(field.getSelector());

            ExtractionField extractionField = new ExtractionField(
                    field.getOutputName(),
                    field.getOutputType(),
                    selectedValue.asText()
            );

            extractionFieldList.add(extractionField);
        }

        return new ExtractionResult(extractionFieldList);
    }
}
