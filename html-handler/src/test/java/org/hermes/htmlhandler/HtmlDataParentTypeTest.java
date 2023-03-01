package org.hermes.htmlhandler;

import org.hermes.core.avro.ExtractedField;
import org.hermes.core.avro.HermesHtmlIngressRecord;
import org.hermes.core.avro.ParentType;
import org.hermes.htmlhandler.extraction.HtmlDataExtractor;
import org.hermes.htmlhandler.util.HtmlDataExtractorUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;
import java.net.URL;
import java.util.List;

import static org.hermes.htmlhandler.util.HtmlDataExtractorUtil.extractValueByNameField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HtmlDataParentTypeTest {

    private final static String DATA_FILE_NAME = "HtmlDataParentTypeTest.html";

    private List<ExtractedField> extractedFields;

    @BeforeAll
    public void setup() throws IOException {
        HtmlDataExtractor htmlDataExtractor = new HtmlDataExtractor();

        String data = "";
        URL url = this.getClass().getResource("/" + DATA_FILE_NAME);
        DataInputStream reader = new DataInputStream(new FileInputStream(url.getFile()));
        int nBytesToRead = reader.available();
        if(nBytesToRead > 0) {
            byte[] bytes = new byte[nBytesToRead];
            reader.read(bytes);
            data = new String(bytes);
        }
        reader.close();

        HermesHtmlIngressRecord hermesIngressRecord = HtmlDataExtractorUtil.constructRecord(data, ParentType.DATA);

        extractedFields = htmlDataExtractor.extract(hermesIngressRecord)
                .getExtractedProducts()
                .get(0);
    }

    @Test
    @DisplayName(value = "Functionality for getting back processed single text value")
    public void testReturningSingleTextValue() {
        String singleTextValue = extractValueByNameField(extractedFields, "testSingleTextValue");
        assertEquals(singleTextValue,"Test of single text value functionality");
    }

    @Test
    @DisplayName(value = "Functionality for getting back processed multiple text value")
    public void testReturningMultipleTextValue() {
        String multipleTextValue = extractValueByNameField(extractedFields, "testMultipleTextValue");
        assertTrue(multipleTextValue.split(",").length > 1);
    }

    @Test
    @DisplayName(value = "Functionality for getting back processed single attribute text value")
    public void testReturningSingleAttrValue() {
        String singleAttrValue = extractValueByNameField(extractedFields, "testSingleAttrValue");
        assertEquals(singleAttrValue,"11");
    }

    @Test
    @DisplayName(value = "Functionality for getting back processed multiple attribute text value")
    public void testReturningMultipleAttrValue() {
        String multipleAttrValue = extractValueByNameField(extractedFields, "testMultipleAttrValue");
        assertTrue(multipleAttrValue.split(",").length > 1);
    }

}
