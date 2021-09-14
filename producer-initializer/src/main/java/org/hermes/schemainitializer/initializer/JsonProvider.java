package org.hermes.schemainitializer.initializer;

import org.apache.avro.specific.SpecificRecord;
import org.hermes.core.avro.HermesJsonIngressRecord;
import org.hermes.core.avro.JsonExtractingParams;
import org.hermes.core.avro.JsonScrapingField;
import org.hermes.core.avro.OutputQuantity;
import org.hermes.core.avro.ParentType;
import org.hermes.schemainitializer.util.ProviderUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@ConditionalOnProperty(prefix = "hermes.initializer.json", name = "status", havingValue = "true")
public class JsonProvider implements Provider {

    @Value(value = "${hermes.initializer.json.topic}")
    private String topic;

    @Value(value = "${hermes.initializer.json.file-name}")
    private String dataFileName;

    @Override
    public SpecificRecord getRecord() {
        return HermesJsonIngressRecord.newBuilder()
                .setData(ProviderUtils.loadContentFromResourceFile(dataFileName))
                .setExtractingParams(getExtractingParams())
                .setConstantFields(Collections.emptyList())
                .build();
    }

    @Override
    public String getTopic() {
        return topic;
    }

    private JsonExtractingParams getExtractingParams() {
        return JsonExtractingParams.newBuilder()
                .setParentType(ParentType.DATA)
                .setScrapingFields(getScrapingField())
                .build();
    }

    private List<JsonScrapingField> getScrapingField() {
        JsonScrapingField scrapingField = JsonScrapingField.newBuilder()
                .setOutputName("id")
                .setOutputQuantity(OutputQuantity.SINGLE)
                .setSelector("/menu/id")
                .build();

        return Collections.singletonList(scrapingField);
    }
}
