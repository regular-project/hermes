package org.hermes.developproducer.service;

import org.hermes.core.avro.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HermesDevelopProducerService {

    @Value("${hermes.kafka.producer.topic}")
    private String topic;

    @Value("${hermes.kafka.producer.key}")
    private String producerKey;

    private final KafkaTemplate<String, HermesIngressRecord> kafkaTemplate;

    public HermesDevelopProducerService(KafkaTemplate<String, HermesIngressRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    private void sendMessage() {
        String data = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Документ</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div id=\"dataKeeper\">Data</div>\n" +
                "        <ul>\n" +
                "            <li style=\"background-color:red\">Осторожно</li>\n" +
                "            <li class=\"info\">Информация</li>\n" +
                "        </ul>\n" +
                "        <ul>\n" +
                "            <li style=\"background-color:red\">Осторожно</li>\n" +
                "            <li class=\"info\">Информация</li>\n" +
                "        </ul>\n" +
                "        <ul>\n" +
                "            <li style=\"background-color:red\">Осторожно</li>\n" +
                "            <li class=\"info\">Информация</li>\n" +
                "        </ul>\n" +
                "        <div id=\"footer\">Made in Russia &copy;</div>\n" +
                "    </body>\n" +
                "</html>\n";

        ExtractingParams extractingParams = getExtractingParams();

        HermesIngressRecord hermesIngressRecord = HermesIngressRecord.newBuilder()
                .setData(data)
                .setExtractingParams(extractingParams)
                .setConstantsFields(Collections.emptyList())
                .build();

        kafkaTemplate.send(topic, producerKey, hermesIngressRecord);

        ListenableFuture<SendResult<String, HermesIngressRecord>> future =
                kafkaTemplate.send(topic, producerKey, hermesIngressRecord);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, HermesIngressRecord> result) {
                System.out.println("Sent message with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message due to : " + ex.getMessage());
            }
        });
    }

    private ExtractingParams getExtractingParams() {
        ParentNodeDataType recordType = ParentNodeDataType.SINGLE;
        List<ScrapingField> scrapingFieldList = getScrapingField();

        return ExtractingParams.newBuilder()
                .setParentNodeDataType(recordType)
                .setParentNode(null)
                .setScrapingFields(scrapingFieldList)
                .build();
    }

    private List<ScrapingField> getScrapingField() {
        List<ScrapingField> scrapingFieldList = new ArrayList<>();

        ScrapingField scrapingField = ScrapingField.newBuilder()
                .setOutputName("name")
                .setOutputType(OutputType.SINGLE)
                .setSelector("li.info")
                .build();

        scrapingFieldList.add(scrapingField);

        return scrapingFieldList;
    }

}
