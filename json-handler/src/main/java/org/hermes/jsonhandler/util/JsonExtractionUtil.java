package org.hermes.jsonhandler.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.hermes.core.avro.JsonScrapingField;

public final class JsonExtractionUtil {

    private JsonExtractionUtil() { }

    public static String extractMultipleValue(JsonNode jsonNode, JsonScrapingField scrapingField) {
        return jsonNode.at(scrapingField.getSelector()).asText();
    }

    public static String extractSingleValue(JsonNode jsonNode, JsonScrapingField scrapingField) {
        return jsonNode.at(scrapingField.getSelector()).asText();
    }
}
