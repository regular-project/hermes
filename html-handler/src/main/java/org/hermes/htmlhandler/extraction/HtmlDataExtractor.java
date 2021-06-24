package org.hermes.htmlhandler.extraction;

import org.hermes.core.avro.*;
import org.hermes.core.extraction.DataExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HtmlDataExtractor implements DataExtractor {

    @Override
    public HermesEgressRecord extract(HermesIngressRecord record) {
        List<List<ExtractedField>> listOfProducts = new ArrayList<>();

        Document document = Jsoup.parse(record.getData());
        ExtractingParams extractingParams = record.getExtractingParams();
        Elements parentsElements = document.select(extractingParams.getParentNode());

        if (extractingParams.getRecordType().equals(RecordType.NEW)) {
            for (Element parentElement: parentsElements) {
                List<ExtractedField> extractedFieldList = extractFieldsFromElement(parentElement, extractingParams);

                listOfProducts.add(extractedFieldList);
            }
        } else {
            List<ExtractedField> extractedFieldList = extractFieldsFromElement(document, extractingParams);

            listOfProducts.add(extractedFieldList);
        }

        return new HermesEgressRecord(extractingParams.getRecordType(), listOfProducts, record.getConstantFields());
    }

    private List<ExtractedField> extractFieldsFromElement(Element element, ExtractingParams extractingParams) {
        List<ExtractedField> extractedFieldList = new ArrayList<>(extractingParams.getScrapingFields().size());

        for (ScrapingField scrapingField: extractingParams.getScrapingFields()) {
            String extractedValue;

            if (scrapingField.getOutputType().equals(OutputType.MULTIPLE)) {
                Elements elements =  element.select(scrapingField.getSelector());

                if (scrapingField.getSelectorParam().equals(SelectorParam.ATTR)) {

                    extractedValue = elements.stream()
                            .map((element1) -> element1.attr(scrapingField.getAttributeName()))
                            .collect(Collectors.toList())
                            .toString();
                } else {
                    extractedValue = elements.stream()
                            .map(Element::text)
                            .collect(Collectors.toList())
                            .toString();
                }
            } else {
                if (scrapingField.getSelectorParam().equals(SelectorParam.ATTR)) {
                    extractedValue = element
                            .select(scrapingField.getSelector())
                            .get(0)
                            .attr(scrapingField.getAttributeName());
                } else {
                    extractedValue = element
                            .select(scrapingField.getSelector())
                            .get(0)
                            .text();
                }
            }

            extractedFieldList.add(new ExtractedField(
                    scrapingField.getOutputName(),
                    scrapingField.getOutputType(),
                    extractedValue)
            );
        }

        return extractedFieldList;
    }
}
