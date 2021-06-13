package org.hermes.htmlhandler.extraction;

import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.avro.ExtractionField;
import org.hermes.core.avro.Field;
import org.hermes.core.avro.OutputType;
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
        List<ExtractionField> extractionFieldList = new ArrayList<>(record.getFields().size());
        Document document = Jsoup.parse(record.getData());

        for (Field field : record.getFields()) {
            ExtractionField.Builder extractionFieldBuilder = ExtractionField.newBuilder()
                .setOutputName(field.getOutputName())
                .setOutputType(field.getOutputType());

            Elements elements = document.select(field.getSelector());

            if (field.getOutputType().equals(OutputType.SINGLE)) {
                String firstStr = elements.get(0).text();

                extractionFieldBuilder.setOutputValue(firstStr);
            } else {
                String outputValue = elements.stream().map(Element::text).collect(Collectors.toList()).toString();

                extractionFieldBuilder.setOutputValue(outputValue);
            }

            extractionFieldList.add(extractionFieldBuilder.build());
        }

        return new HermesEgressRecord(extractionFieldList);
    }
}
