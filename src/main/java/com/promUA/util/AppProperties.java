package com.promUA.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private static Properties properties;

    static {
        try (InputStream propertiesFile = AppProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties = new Properties();
            properties.load(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDefaultPassword() {
        return getProperty("default.password");
    }

    public static String getDefaultEmailName() {
        return getProperty("default.email.name");
    }

    public static String getDefaultEmailDomain() {
        return getProperty("default.email.domain");
    }

    /**
     * if property is missing in property file
     * then try to find property in System.properties
     */
    private static String getProperty(String key) {
        String value = properties.getProperty(key);
        return value == null ? "" : value;
    }

    public static String getEmailLogin() {
        return getProperty("email.login");
    }

    public static String getEmailPassword() {
        return getProperty("email.password");
    }
}
