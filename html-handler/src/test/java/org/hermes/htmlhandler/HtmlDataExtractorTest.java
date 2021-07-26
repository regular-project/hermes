package org.hermes.htmlhandler;

import org.hermes.core.avro.ExtractedField;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.avro.ParentNodeDataType;
import org.hermes.core.utils.exception.DefaultExtractorException;
import org.hermes.htmlhandler.extraction.HtmlDataExtractor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hermes.htmlhandler.HtmlDataExtractorUtil.extractValueByNameField;
import static org.junit.jupiter.api.Assertions.*;

class HtmlDataExtractorTest {

    @Test
    public void testFunctionalityOfSingleDataType() throws DefaultExtractorException {
        HtmlDataExtractor htmlDataExtractor = new HtmlDataExtractor();

        HermesIngressRecord hermesIngressRecord = HtmlDataExtractorUtil.prepareHermesIngressRecord(ParentNodeDataType.SINGLE);

        List<ExtractedField> extractedFieldList = htmlDataExtractor.extract(hermesIngressRecord)
                .getExtractedProducts()
                .get(0);

        String singleTextValue = extractValueByNameField(extractedFieldList, "testSingleTextValue");
        assertEquals(singleTextValue,"Test of single text value functionality");

        String multipleTextValue = extractValueByNameField(extractedFieldList, "testMultipleTextValue");
        assertTrue(multipleTextValue.split(",").length > 1);

        String singleAttrValue = extractValueByNameField(extractedFieldList, "testSingleAttrValue");
        assertEquals(singleAttrValue,"1");

        String multipleAttrValue = extractValueByNameField(extractedFieldList, "testMultipleAttrValue");
        assertTrue(multipleAttrValue.split(",").length > 1);
    }

    @Test
    public void testFunctionalityOfListDataType() throws DefaultExtractorException {
        HtmlDataExtractor htmlDataExtractor = new HtmlDataExtractor();

        HermesIngressRecord hermesIngressRecord = HtmlDataExtractorUtil.prepareHermesIngressRecord(ParentNodeDataType.LIST);

        List<List<ExtractedField>> itemsExtractedFields = htmlDataExtractor.extract(hermesIngressRecord)
                .getExtractedProducts();

        assertEquals(itemsExtractedFields.size(), 2);
    }

}
