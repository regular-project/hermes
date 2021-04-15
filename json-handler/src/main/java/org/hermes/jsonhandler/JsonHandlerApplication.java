package org.hermes.jsonhandler;


import org.hermes.jsonhandler.service.JsonHandlerService;

public class JsonHandlerApplication {

    public static void main(String[] args) throws Exception {
        JsonHandlerService jsonHandlerService = new JsonHandlerService();
        jsonHandlerService.start();
    }
}
