package org.hermes.jsonhandler.extraction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.specific.SpecificRecord;
import org.hermes.core.avro.ExtractedField;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesJsonIngressRecord;
import org.hermes.core.avro.JsonExtractingParams;
import org.hermes.core.avro.JsonScrapingField;
import org.hermes.core.avro.ParentType;
import org.hermes.core.extraction.DataExtractor;
import org.hermes.core.utils.exception.ExtractorException;
import org.hermes.jsonhandler.processor.JsonScrapingFieldProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonDataExtractor implements DataExtractor {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public HermesEgressRecord extract(SpecificRecord record) {
        List<List<ExtractedField>> productsList = new ArrayList<>();

        HermesJsonIngressRecord hermesIngressRecord = (HermesJsonIngressRecord) record;
        JsonExtractingParams extractingParams = hermesIngressRecord.getExtractingParams();

        try {
            JsonNode dataNode = mapper.readTree(hermesIngressRecord.getData());

            if (extractingParams.getParentType().equals(ParentType.CUSTOM)) {
                JsonNode customParentNodes = dataNode.at(extractingParams.getParentSelector());

                for (JsonNode jsonNode: customParentNodes) {
                    productsList.add(extractFieldsFromElement(jsonNode, extractingParams.getScrapingFields()));
                }
            } else {
                productsList.add(extractFieldsFromElement(dataNode, extractingParams.getScrapingFields()));
            }
        } catch (Exception e) {
            throw new ExtractorException("An error occurred in json extractor", e);
        }

        return new HermesEgressRecord(productsList, hermesIngressRecord.getConstantFields());
    }

    private List<ExtractedField> extractFieldsFromElement(JsonNode jsonNode, List<JsonScrapingField> scrapingFields) {
        List<ExtractedField> extractedFields = new ArrayList<>();

        for (JsonScrapingField jsonScrapingField: scrapingFields) {
            JsonScrapingFieldProcessor processor = (JsonScrapingFieldProcessor) jsonScrapingField;

            extractedFields.add(processor.processScrapingField(jsonNode));
        }

        return extractedFields;
    }
}
