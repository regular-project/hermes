package org.hermes.htmlhandler.extraction;

import org.apache.avro.specific.SpecificRecord;
import org.hermes.core.avro.ExtractedField;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesHtmlIngressRecord;
import org.hermes.core.avro.HtmlExtractingParams;
import org.hermes.core.avro.HtmlScrapingField;
import org.hermes.core.avro.OutputQuantity;
import org.hermes.core.avro.ParentType;
import org.hermes.core.avro.SelectorParam;
import org.hermes.core.extraction.DataExtractor;
import org.hermes.core.utils.exception.ExtractorException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hermes.htmlhandler.util.HtmlExtractionUtil.extractMultipleStringValueByAttr;
import static org.hermes.htmlhandler.util.HtmlExtractionUtil.extractMultipleStringValueByText;
import static org.hermes.htmlhandler.util.HtmlExtractionUtil.extractSingleStringValueByAttr;
import static org.hermes.htmlhandler.util.HtmlExtractionUtil.extractSingleStringValueByText;

@Component
public class HtmlDataExtractor implements DataExtractor {

    @Override
    public HermesEgressRecord extract(SpecificRecord record) {
        List<List<ExtractedField>> productsList = new ArrayList<>();

        HermesHtmlIngressRecord hermesIngressRecord = (HermesHtmlIngressRecord) record;
        HtmlExtractingParams extractingParams = hermesIngressRecord.getExtractingParams();

        try {
            Document document = Jsoup.parse(hermesIngressRecord.getData());
            List<HtmlScrapingField> scrapingFields = extractingParams.getScrapingFields();

            if (extractingParams.getParentType().equals(ParentType.CUSTOM)) {
                Elements customParentNodes = document.select(extractingParams.getParentSelector());

                for (Element parentNode: customParentNodes) {
                    productsList.add(extractFieldsFromElement(parentNode, scrapingFields));
                }
            } else {
                productsList.add(extractFieldsFromElement(document, scrapingFields));
            }
        } catch (Exception e) {
            throw new ExtractorException("An error occurred in html extractor", e);
        }

        return new HermesEgressRecord(productsList, hermesIngressRecord.getConstantFields());
    }

    private List<ExtractedField> extractFieldsFromElement(Element element, List<HtmlScrapingField> scrapingFields) {
        return scrapingFields.stream().map(scrapingField ->
                    ExtractedField.newBuilder()
                            .setOutputName(scrapingField.getOutputName())
                            .setOutputValue(extractValue(element, scrapingField))
                            .setOutputQuantity(scrapingField.getOutputQuantity())
                            .build())
                .collect(Collectors.toList());
    }

    private String extractValue(Element element, HtmlScrapingField scrapingField) {
        if (scrapingField.getOutputQuantity().equals(OutputQuantity.MULTIPLE)) {
            Elements elements = element.select(scrapingField.getSelector());

            if (scrapingField.getSelectorParam().equals(SelectorParam.TEXT)) {
                return extractMultipleStringValueByText(elements);
            } else {
                return extractMultipleStringValueByAttr(elements, scrapingField.getAttributeName());
            }
        } else {
            if (scrapingField.getSelectorParam().equals(SelectorParam.TEXT)) {
                return extractSingleStringValueByText(element, scrapingField.getSelector());
            } else {
                return extractSingleStringValueByAttr(element, scrapingField);
            }
        }
    }
}
