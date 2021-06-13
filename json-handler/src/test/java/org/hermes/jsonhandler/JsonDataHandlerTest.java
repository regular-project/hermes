package org.hermes.jsonhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hermes.core.avro.*;
import org.hermes.jsonhandler.extraction.JsonDataExtractor;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JsonDataHandlerTest {

    @Test
    public void testIsExtractorReturnsSingleValue() throws JsonProcessingException {
        JsonDataExtractor htmlDataExtractor = new JsonDataExtractor();

        HermesIngressRecord hermesIngressRecord = prepareHermesIngressRecord(OutputType.SINGLE);

        ExtractionField testedExtractionField = htmlDataExtractor.extract(hermesIngressRecord)
                .getExtractedFields()
                .get(0);

        assertNotNull(testedExtractionField.getOutputValue());

        assertFalse(testedExtractionField.getOutputValue().split(",").length != 1);
    }

    @Test
    public void testIsExtractorReturnsMultipleValue() throws JsonProcessingException {
        JsonDataExtractor jsonDataExtractor = new JsonDataExtractor();

        HermesIngressRecord hermesIngressRecord = prepareHermesIngressRecord(OutputType.MULTIPLE);

        ExtractionField testedExtractionField = jsonDataExtractor.extract(hermesIngressRecord)
                .getExtractedFields()
                .get(0);

        assertNotNull(testedExtractionField.getOutputValue());

        assertTrue(testedExtractionField.getOutputValue().split(",").length != 1);
    }

    private HermesIngressRecord prepareHermesIngressRecord(OutputType outputType) {
        Field field = new Field("/members", outputType, OutputTopic.FINAL, "textValue");

        return HermesIngressRecord.newBuilder()
                .setData("{\n" +
                        "  \"squadName\": \"Super hero squad\",\n" +
                        "  \"members\": [\n" +
                        "  \"Nick\",\n" +
                        "  \"Petr\",\n" +
                        "  \"Mike\"\n" +
                        "  ]\n" +
                        "}")
                .setFields(Collections.singletonList(field))
                .build();
    }

}
