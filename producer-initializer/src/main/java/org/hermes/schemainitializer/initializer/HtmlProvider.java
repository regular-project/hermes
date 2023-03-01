package org.hermes.schemainitializer.initializer;

import org.apache.avro.specific.SpecificRecord;
import org.hermes.core.avro.HermesHtmlIngressRecord;
import org.hermes.core.avro.HtmlExtractingParams;
import org.hermes.core.avro.HtmlScrapingField;
import org.hermes.core.avro.OutputQuantity;
import org.hermes.core.avro.ParentType;
import org.hermes.schemainitializer.util.ProviderUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@ConditionalOnProperty(prefix = "hermes.initializer.html", name = "status", havingValue = "true")
public class HtmlProvider implements Provider {

    @Value(value = "${hermes.initializer.html.topic}")
    private String topic;

    @Value(value = "${hermes.initializer.html.file-name}")
    private String dataFileName;

    @Override
    public SpecificRecord getRecord() {
        return HermesHtmlIngressRecord.newBuilder()
                .setData(ProviderUtils.loadContentFromResourceFile(dataFileName))
                .setExtractingParams(getExtractingParams())
                .setConstantFields(Collections.emptyList())
                .build();
    }

    @Override
    public String getTopic() {
        return topic;
    }

    private HtmlExtractingParams getExtractingParams() {
        return HtmlExtractingParams.newBuilder()
                .setParentType(ParentType.DATA)
                .setScrapingFields(getScrapingField())
                .build();
    }

    private List<HtmlScrapingField> getScrapingField() {
        HtmlScrapingField scrapingField = HtmlScrapingField.newBuilder()
                .setOutputName("name")
                .setOutputQuantity(OutputQuantity.MULTIPLE)
                .setSelector("li.info")
                .build();

        return Collections.singletonList(scrapingField);
    }
}
