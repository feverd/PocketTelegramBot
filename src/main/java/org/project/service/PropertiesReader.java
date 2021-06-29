package org.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    public String getPropertyValue(String parameterName) {
        String value = "";
        Properties properties = new Properties();

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
            value = properties.getProperty(parameterName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

}
