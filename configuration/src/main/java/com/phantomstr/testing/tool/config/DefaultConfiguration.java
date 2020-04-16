package com.phantomstr.testing.tool.config;

import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultConfiguration {
    @SneakyThrows
    public static String getDefaultValue(String key) {
        Properties properties = new Properties();
        String defaultPropertiesFileName = "configuration.properties";
        InputStream inputStream = DefaultConfiguration.class.getClassLoader().getResourceAsStream(defaultPropertiesFileName);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file " + defaultPropertiesFileName + " not found in classpath");
        }
        return properties.getProperty(key);
    }
}
