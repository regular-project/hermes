package org.hermes.jsonhandler.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.hermes.core.avro.ExtractedField;
import org.hermes.core.avro.JsonScrapingField;


public class JsonScrapingFieldProcessor extends JsonScrapingField {

    public ExtractedField processScrapingField(JsonNode jsonNode, JsonScrapingField scrapingField) {
        String extractedValue;

        if (scrapingField.getOutputQuantity().equals(org.hermes.core.avro.OutputQuantity.MULTIPLE)) {
            extractedValue = JsonExtractionUtil.extractMultipleValue(jsonNode, scrapingField);
        } else {
            extractedValue = JsonExtractionUtil.extractSingleValue(jsonNode, scrapingField);
        }

        return ExtractedField.newBuilder()
                .setOutputName(scrapingField.getOutputName())
                .setOutputValue(extractedValue)
                .setOutputQuantity(scrapingField.getOutputQuantity())
                .build();
    }

}
