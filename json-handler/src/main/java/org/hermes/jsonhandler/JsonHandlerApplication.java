package org.hermes.jsonhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.hermes.jsonhandler", "org.hermes.core"})
public class JsonHandlerApplication {

    public static void main(String[] args) {

        SpringApplication.run(JsonHandlerApplication.class, args);
    }

}
