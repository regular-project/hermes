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
        String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
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

        ExtractingParams extractingParams = new ExtractingParams();

        List<ScrapingField> scrapingFieldList = new ArrayList<>();

        ScrapingField scrapingFieldAttr = new ScrapingField();

        scrapingFieldAttr.setOutputName("style");
        scrapingFieldAttr.setAttributeName("style");
        scrapingFieldAttr.setOutputType(OutputType.SINGLE);
        scrapingFieldAttr.setSelector("li[style]");
        scrapingFieldAttr.setSelectorParam(SelectorParam.ATTR);

        ScrapingField scrapingFieldText = new ScrapingField();

        scrapingFieldText.setOutputName("info");
        scrapingFieldText.setAttributeName(null);
        scrapingFieldText.setOutputType(OutputType.SINGLE);
        scrapingFieldText.setSelector("li.info");
        scrapingFieldText.setSelectorParam(SelectorParam.TEXT);

        scrapingFieldList.add(scrapingFieldAttr);
        scrapingFieldList.add(scrapingFieldText);

        extractingParams.setParentNode("ul");
        extractingParams.setRecordType(RecordType.NEW);
        extractingParams.setScrapingFields(scrapingFieldList);

        HermesIngressRecord hermesIngressRecord = HermesIngressRecord.newBuilder()
                .setData(html)
                .setExtractingParams(extractingParams)
                .setConstantFields(new ArrayList<>())
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
}
