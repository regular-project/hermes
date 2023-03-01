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

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HtmlCustomParentTypeTest {

    private final static String DATA_FILE_NAME = "HtmlCustomParentTypeTest.html";

    private List<List<ExtractedField>> extractedFields;

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

         HermesHtmlIngressRecord hermesIngressRecord = HtmlDataExtractorUtil.constructRecord(data, ParentType.CUSTOM);

        extractedFields = htmlDataExtractor.extract(hermesIngressRecord).getExtractedProducts();
    }

    @Test
    @DisplayName(value = "Functionality for parsing data with custom parents inside data")
    public void testCustomParent() {
        assertTrue(extractedFields.size() > 1);
    }
}
