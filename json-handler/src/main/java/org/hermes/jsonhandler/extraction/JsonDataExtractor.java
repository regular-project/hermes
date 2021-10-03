package org.hermes.jsonhandler.extraction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.specific.SpecificRecord;
import org.hermes.core.avro.ExtractedField;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesJsonIngressRecord;
import org.hermes.core.avro.JsonExtractingParams;
import org.hermes.core.avro.JsonScrapingField;
import org.hermes.core.avro.OutputQuantity;
import org.hermes.core.avro.ParentType;
import org.hermes.core.extraction.DataExtractor;
import org.hermes.core.util.exception.ExtractorException;
import org.hermes.jsonhandler.util.JsonExtractionUtil;
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
            extractedFields.add(extractField(jsonScrapingField, jsonNode));
        }

        return extractedFields;
    }

    private ExtractedField extractField(JsonScrapingField jsonScrapingField, JsonNode jsonNode) {
        String extractedValue;

        ExtractedField extractedField = ExtractedField.newBuilder()
                .setOutputQuantity(jsonScrapingField.getOutputQuantity())
                .setOutputName(jsonScrapingField.getOutputName()).build();

        if (jsonScrapingField.getOutputQuantity().equals(OutputQuantity.MULTIPLE)) {
            extractedValue = JsonExtractionUtil.extractMultipleValue(jsonNode, jsonScrapingField);
        } else {
            extractedValue = JsonExtractionUtil.extractSingleValue(jsonNode, jsonScrapingField);
        }

        extractedField.setOutputValue(extractedValue);

        return extractedField;
    }
}
