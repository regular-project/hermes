package org.hermes.htmlhandler.extraction;

import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.ExtractedField;
import org.hermes.core.avro.ParentNodeDataType;
import org.hermes.core.avro.ScrapingField;
import org.hermes.core.avro.OutputType;
import org.hermes.core.avro.SelectorParam;
import org.hermes.core.avro.ExtractingParams;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.extraction.DataExtractor;
import org.hermes.htmlhandler.util.HtmlExtractionException;
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
    public HermesEgressRecord extract(HermesIngressRecord record) throws HtmlExtractionException {
        List<List<ExtractedField>> listOfProducts = new ArrayList<>();

        ExtractingParams extractingParams = record.getExtractingParams();

        try {
            Document document = Jsoup.parse(record.getData());
            List<ScrapingField> scrapingFields = extractingParams.getScrapingFields();

            if (extractingParams.getParentNodeDataType().equals(ParentNodeDataType.LIST)) {
                document.select(extractingParams.getParentNode())
                        .forEach(parentElement ->
                                listOfProducts.add(extractFieldsFromElement(parentElement, scrapingFields))
                        );
            } else {
                listOfProducts.add(extractFieldsFromElement(document, scrapingFields));
            }
        } catch (Exception e) {
            throw new HtmlExtractionException("An error occurred in html extractor", e);
        }

        return new HermesEgressRecord(listOfProducts, record.getConstantsFields());
    }

    private List<ExtractedField> extractFieldsFromElement(Element element, List<ScrapingField> scrapingFields) {
        return scrapingFields.stream().map(scrapingField ->
                new ExtractedField(
                        scrapingField.getOutputName(),
                        scrapingField.getOutputType(),
                        extractValue(element, scrapingField)
                )).collect(Collectors.toList());

    }

    private String extractValue(Element element, ScrapingField scrapingField) {
        if (scrapingField.getOutputType().equals(OutputType.MULTIPLE)) {
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
