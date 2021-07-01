package org.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationPropertiesReader {
    private static ApplicationPropertiesReader instance;
    private Properties properties;
    private String fileName = "application.properties";

    private ApplicationPropertiesReader() {
        properties = new Properties();
        readProperties();
    }

    private void readProperties() {
        try (InputStream input = ApplicationPropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ApplicationPropertiesReader getInstance() {
        if (instance == null) {
            instance = new ApplicationPropertiesReader();
            instance.readProperties();
        }
        return instance;
    }

    public static String getProperty(String parameterName) {
        return getInstance().properties.getProperty(parameterName).trim();
    }


}
