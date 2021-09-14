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
import org.hermes.jsonhandler.util.JsonScrapingFieldProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            List<JsonScrapingField> scrapingFields = extractingParams.getScrapingFields();

            if (extractingParams.getParentType().equals(ParentType.CUSTOM)) {
                JsonNode customParentNodes = dataNode.at(extractingParams.getParentSelector());

                for (JsonNode jsonNode: customParentNodes) {
                    productsList.add(extractFieldsFromElement(jsonNode, scrapingFields));
                }
            } else {
                productsList.add(extractFieldsFromElement(dataNode, scrapingFields));
            }
        } catch (Exception e) {
            throw new ExtractorException("An error occurred in json extractor", e);
        }

        return new HermesEgressRecord(productsList, hermesIngressRecord.getConstantFields());
    }

    private List<ExtractedField> extractFieldsFromElement(JsonNode jsonNode, List<JsonScrapingField> scrapingFields) {
        return scrapingFields.stream().map(scrapingField -> {
            JsonScrapingFieldProcessor processor = (JsonScrapingFieldProcessor) scrapingFields;

            return processor.processScrapingField(jsonNode, scrapingField);
        }).collect(Collectors.toList());
    }
}
