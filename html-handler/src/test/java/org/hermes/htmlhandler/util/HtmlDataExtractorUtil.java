package org.hermes.htmlhandler.util;

import org.hermes.core.avro.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HtmlDataExtractorUtil {

    public static HermesHtmlIngressRecord constructRecord(String data, ParentType parentType) {
        return HermesHtmlIngressRecord.newBuilder()
                .setData(data)
                .setExtractingParams(getExtractingParams(parentType))
                .setConstantFields(Collections.emptyList())
                .build();
    }

    private static HtmlExtractingParams getExtractingParams(ParentType dataType) {
        String parentSelector = dataType.equals(ParentType.CUSTOM) ? "div.multipleParentType" : null;

        return HtmlExtractingParams.newBuilder()
                .setParentSelector(parentSelector)
                .setScrapingFields(getScrapingFieldList())
                .setParentType(dataType)
                .build();
    }

    private static List<HtmlScrapingField> getScrapingFieldList() {
        List<HtmlScrapingField> scrapingFieldList = new ArrayList<>();

        scrapingFieldList.add(constructHtmlScrapingField(
                "testSingleTextValue",
                OutputQuantity.SINGLE,
                SelectorParam.TEXT,
                "h1.singleTextValue",
                null)
        );

        scrapingFieldList.add(constructHtmlScrapingField(
                "testMultipleTextValue",
                OutputQuantity.MULTIPLE,
                SelectorParam.TEXT,
                "h1.multipleTextValue",
                null)
        );

        scrapingFieldList.add(constructHtmlScrapingField(
                "testSingleAttrValue",
                OutputQuantity.SINGLE,
                SelectorParam.ATTR,
                "h2.singleAttrValue",
                "id")
        );

        scrapingFieldList.add(constructHtmlScrapingField(
                "testMultipleAttrValue",
                OutputQuantity.MULTIPLE,
                SelectorParam.ATTR,
                "h2.multipleAttrValue",
                "id")
        );

        return scrapingFieldList;
    }

    private static HtmlScrapingField constructHtmlScrapingField(String outputName, OutputQuantity outputQuantity, SelectorParam selectorParam, String selector, String attrName) {
        return HtmlScrapingField.newBuilder()
                .setOutputName(outputName)
                .setOutputQuantity(outputQuantity)
                .setSelectorParam(selectorParam)
                .setSelector(selector)
                .setAttributeName(attrName)
                .build();
    }

    public static String extractValueByNameField(List<ExtractedField> extractedFieldList, String name) {
        return extractedFieldList
                .stream()
                .filter(extractedField -> extractedField.getOutputName().equals(name))
                .findFirst()
                .orElseThrow().getOutputValue();
    }
}
