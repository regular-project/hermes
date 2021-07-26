package org.hermes.htmlhandler;

import org.hermes.core.avro.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HtmlDataExtractorUtil {

    public static HermesIngressRecord prepareHermesIngressRecord(ParentNodeDataType dataType) {
        ExtractingParams extractingParams = prepareExtractingParams(dataType);

        return HermesIngressRecord.newBuilder()
                .setData("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "  <body>\n" +
                "    <div class=\"singleParentNodeType\"> Test of single(document) parent node type\n" +

                "    <h1 class=\"singleTextValue\">Test of single text value functionality</h1>\n" +

                "    <h1 class=\"multipleTextValue\">Test № 1 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 2 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 3 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 4 of multiple text value functionality</h1>\n" +

                "    <h2 class=\"singleAttrValue\" id=\"1\">Test of single attribute value functionality</h2>\n" +

                "    <h2 class=\"multipleAttrValue\" id=\"1\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"2\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"3\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"4\">Test of multiple attribute value functionality</h2>\n" +

                "    </div>\n" +


                "    <div class=\"multipleParentNodeType\"> Test of multiple parent node type\n" +

                "    <h1 class=\"singleTextValue\">Test of single text value functionality</h1>\n" +

                "    <h1 class=\"multipleTextValue\">Test № 1 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 2 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 3 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 4 of multiple text value functionality</h1>\n" +

                "    <h2 class=\"singleAttrValue\" id=\"1\">Test of single attribute value functionality</h2>\n" +

                "    <h2 class=\"multipleAttrValue\" id=\"1\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"2\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"3\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"4\">Test of multiple attribute value functionality</h2>\n" +

                "    </div>\n" +

                "    <div class=\"multipleParentNodeType\"> Test of Test of multiple parent node type\n" +

                "    <h1 class=\"singleTextValue\">Test of single text value functionality</h1>\n" +

                "    <h1 class=\"multipleTextValue\">Test № 1 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 2 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 3 of multiple text value functionality</h1>\n" +
                "    <h1 class=\"multipleTextValue\">Test № 4 of multiple text value functionality</h1>\n" +

                "    <h2 class=\"singleAttrValue\" id=\"1\">Test of single attribute value functionality</h2>\n" +

                "    <h2 class=\"multipleAttrValue\" id=\"1\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"2\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"3\">Test of multiple attribute value functionality</h2>\n" +
                "    <h2 class=\"multipleAttrValue\" id=\"4\">Test of multiple attribute value functionality</h2>\n" +

                "    </div>\n" +
                "  </body>\n" +
                "</html>")
                .setExtractingParams(extractingParams)
                .setConstantsFields(Collections.emptyList())
                .build();
    }

    private static ExtractingParams prepareExtractingParams(ParentNodeDataType dataType) {
        List<ScrapingField> scrapingFields = prepareScrapingFieldList();

        String parentNode = dataType.equals(ParentNodeDataType.LIST) ? "div.multipleParentNodeType" : null;

        return ExtractingParams.newBuilder()
                .setParentNodeDataType(dataType)
                .setScrapingFields(scrapingFields)
                .setParentNode(parentNode)
                .build();
    }

    private static List<ScrapingField> prepareScrapingFieldList() {
        List<ScrapingField> scrapingFieldList = new ArrayList<>();

        ScrapingField singleTextScrapingField = ScrapingField.newBuilder()
                .setOutputName("testSingleTextValue")
                .setOutputType(OutputType.SINGLE)
                .setSelectorParam(SelectorParam.TEXT)
                .setSelector("h1.singleTextValue")
                .build();

        ScrapingField multipleTextScrapingField = ScrapingField.newBuilder()
                .setOutputName("testMultipleTextValue")
                .setOutputType(OutputType.MULTIPLE)
                .setSelectorParam(SelectorParam.TEXT)
                .setSelector("h1.multipleTextValue")
                .build();

        ScrapingField singleAttrScrapingField = ScrapingField.newBuilder()
                .setOutputName("testSingleAttrValue")
                .setOutputType(OutputType.SINGLE)
                .setSelectorParam(SelectorParam.ATTR)
                .setSelector("h2.singleAttrValue")
                .setAttributeName("id")
                .build();

        ScrapingField multipleAttrScrapingField = ScrapingField.newBuilder()
                .setOutputName("testMultipleAttrValue")
                .setOutputType(OutputType.MULTIPLE)
                .setSelectorParam(SelectorParam.ATTR)
                .setSelector("h2.multipleAttrValue")
                .setAttributeName("id")
                .build();

        scrapingFieldList.add(singleTextScrapingField);
        scrapingFieldList.add(multipleTextScrapingField);
        scrapingFieldList.add(singleAttrScrapingField);
        scrapingFieldList.add(multipleAttrScrapingField);

        return scrapingFieldList;
    }

    public static String extractValueByNameField(List<ExtractedField> extractedFieldList, String name) {
        return extractedFieldList
                .stream()
                .filter(extractedField -> extractedField.getOutputName().equals(name))
                .findFirst()
                .orElseThrow().getOutputValue();
    }
}
