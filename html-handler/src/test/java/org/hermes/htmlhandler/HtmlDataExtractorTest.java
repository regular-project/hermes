package org.hermes.htmlhandler;

import org.hermes.core.avro.*;
import org.hermes.htmlhandler.extraction.HtmlDataExtractor;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class HtmlDataExtractorTest {

    @Test
    public void testIsExtractorReturnsSingleValue() {
        HtmlDataExtractor htmlDataExtractor = new HtmlDataExtractor();

        HermesIngressRecord hermesIngressRecord = prepareHermesIngressRecord(OutputType.SINGLE);

        ExtractionField testedExtractionField = htmlDataExtractor.extract(hermesIngressRecord)
                .getExtractedFields()
                .get(0);

        assertNotNull(testedExtractionField.getOutputValue());

        assertFalse(testedExtractionField.getOutputValue().split(",").length != 1);
    }

    @Test
    public void testIsExtractorReturnsMultipleValue() {
        HtmlDataExtractor htmlDataExtractor = new HtmlDataExtractor();

        HermesIngressRecord hermesIngressRecord = prepareHermesIngressRecord(OutputType.MULTIPLE);

        ExtractionField testedExtractionField = htmlDataExtractor.extract(hermesIngressRecord)
                .getExtractedFields()
                .get(0);

        assertNotNull(testedExtractionField.getOutputValue());

        assertTrue(testedExtractionField.getOutputValue().split(",").length != 1);
    }

    private HermesIngressRecord prepareHermesIngressRecord(OutputType outputType) {
        Field field = new Field("p.classTest", outputType, OutputTopic.FINAL, "textValue");

        return HermesIngressRecord.newBuilder()
                .setData("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<meta charset=\"UTF-8\">\n" +
                        "</head>\n" +
                        "  <body>\n" +
                        "    <h1>This is test for Hermes Html Extractor</h1>\n" +
                        "    <p class=\"classTest\">Some first text</p>\n" +
                        "    <p class=\"classTest\">Some second text</p>\n" +
                        "    <p class=\"classTest\">Some third text</p>\n" +
                        "  </body>\n" +
                        "</html>")
                .setFields(Collections.singletonList(field))
                .build();
    }
}
