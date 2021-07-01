package org.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


//TODO добавить ли родителя ?
public class BotMessagesReader {
    private static BotMessagesReader instance;
    private Properties properties;
    private String fileName = "botMessages.properties";

    private BotMessagesReader() {
        properties = new Properties();
        readProperties();
    }

    private void readProperties() {
        try (InputStream input = BotMessagesReader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BotMessagesReader getInstance() {
        if (instance == null) {
            instance = new BotMessagesReader();
            instance.readProperties();
        }
        return instance;
    }

    public static String getProperty(String parameterName) {
        return getInstance().properties.getProperty(parameterName).trim();
    }
}
