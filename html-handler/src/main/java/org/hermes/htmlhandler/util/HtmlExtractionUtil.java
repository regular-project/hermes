package org.hermes.htmlhandler.util;

import org.hermes.core.avro.HtmlScrapingField;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.stream.Collectors;

public final class HtmlExtractionUtil {

    private HtmlExtractionUtil() { }

    public static String extractMultipleStringValueByText(Elements elements) {
            return elements.stream()
                    .map(Element::text)
                    .collect(Collectors.toList())
                    .toString();
    }

    public static String extractMultipleStringValueByAttr(Elements elements, String attributeName) {
        return elements.stream()
                .map((element1) -> element1.attr(attributeName))
                .collect(Collectors.toList())
                .toString();
    }

    public static String extractSingleStringValueByText(Element element, String selector) {
        return element
                .selectFirst(selector)
                .text();
    }

    public static String extractSingleStringValueByAttr(Element element, HtmlScrapingField scrapingField) {
        return element
                .selectFirst(scrapingField.getSelector())
                .attr(scrapingField.getAttributeName());
    }

}
