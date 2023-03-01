package org.hermes.jsonhandler.processor;

import com.fasterxml.jackson.databind.JsonNode;
import org.hermes.core.avro.ExtractedField;
import org.hermes.core.avro.JsonScrapingField;
import org.hermes.jsonhandler.util.JsonExtractionUtil;


public class JsonScrapingFieldProcessor extends JsonScrapingField {

    public ExtractedField processScrapingField(JsonNode jsonNode) {
        String extractedValue;

        if (super.getOutputQuantity().equals(org.hermes.core.avro.OutputQuantity.MULTIPLE)) {
            extractedValue = JsonExtractionUtil.extractMultipleValue(jsonNode, this);
        } else {
            extractedValue = JsonExtractionUtil.extractSingleValue(jsonNode, this);
        }

        return ExtractedField.newBuilder()
                .setOutputName(super.getOutputName())
                .setOutputValue(extractedValue)
                .setOutputQuantity(super.getOutputQuantity())
                .build();
    }

}
