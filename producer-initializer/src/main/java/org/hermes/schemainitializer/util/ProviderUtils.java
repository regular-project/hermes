package org.hermes.schemainitializer.util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public final class ProviderUtils {

    private ProviderUtils() { }

    public static String loadContentFromResourceFile(String fileName) {
        String data = "";

        URL url = ProviderUtils.class.getClassLoader().getResource("templates/" + fileName);
        DataInputStream reader;
        try {
            reader = new DataInputStream(new FileInputStream(url.getFile()));
            int nBytesToRead = reader.available();
            if (nBytesToRead > 0) {
                byte[] bytes = new byte[nBytesToRead];
                reader.read(bytes);
                data = new String(bytes);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
