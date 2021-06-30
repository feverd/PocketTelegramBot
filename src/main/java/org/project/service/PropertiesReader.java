package org.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    public static String getApplicationPropertyValue(String parameterName) {
        return getPropertiesFile("application.properties")
                .getProperty(parameterName);
    }

    public static String getBotMessages(String parameterName) {
        return getPropertiesFile("")
                .getProperty(parameterName);
    }

    private static Properties getPropertiesFile(String propertiesFileName) {
        Properties properties = new Properties();

        try (InputStream inputStream = PropertiesReader.class.getClassLoader()
                .getResourceAsStream(propertiesFileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
