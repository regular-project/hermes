package org.hermes.jsonhandler.extraction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.avro.ExtractionField;
import org.hermes.core.avro.Field;
import org.hermes.core.avro.OutputType;
import org.hermes.core.extraction.DataExtractor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonDataExtractor implements DataExtractor {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public HermesEgressRecord extract(HermesIngressRecord record) throws JsonProcessingException {
        List<ExtractionField> extractionFieldList = new ArrayList<>(record.getFields().size());
        JsonNode node = mapper.readTree(record.getData());

        for (Field field : record.getFields()) {
            JsonNode selectedValue = node.at(field.getSelector());

            if (selectedValue.isArray() && field.getOutputType().equals(OutputType.SINGLE)) {
                selectedValue = selectedValue.get(0);
            }

            ExtractionField extractionField = new ExtractionField(
                    field.getOutputName(),
                    field.getOutputType(),
                    selectedValue.toString()
            );

            extractionFieldList.add(extractionField);
        }

        return new HermesEgressRecord(extractionFieldList);
    }
}
