package org.hermes.htmlhandler.util;

import org.hermes.core.avro.HtmlScrapingField;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;
import java.util.stream.Collectors;

public final class HtmlExtractionUtil {

    private HtmlExtractionUtil() { }

    public static String extractMultipleStringValueByText(Elements elements) {
            return Optional.ofNullable(elements.stream()
                    .map(Element::text)
                    .collect(Collectors.toList())
                    .toString()).orElse("");
    }

    public static String extractMultipleStringValueByAttr(Elements elements, String attributeName) {
        return Optional.ofNullable(elements.stream()
                .map((element1) -> element1.attr(attributeName))
                .collect(Collectors.toList())
                .toString()).orElse("");
    }

    public static String extractSingleStringValueByText(Element element, String selector) {
        return Optional.ofNullable(element.selectFirst(selector).text()).orElse("");
    }

    public static String extractSingleStringValueByAttr(Element element, HtmlScrapingField scrapingField) {
        return Optional.ofNullable(element
                .selectFirst(scrapingField.getSelector())
                .attr(scrapingField.getAttributeName())).orElse("");
    }

}
