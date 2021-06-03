package org.hermes.htmlhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.hermes.htmlhandler", "org.hermes.core"})
public class HtmlHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtmlHandlerApplication.class, args);
    }
}
