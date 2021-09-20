package io.github.phantomstr.testing.tool.config.core;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;

import java.io.InputStream;

import static java.lang.String.format;

public class ConfigLoader {
    public static Configuration getConfig(InputStream inputStream) {
        try {
            FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                    new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class);
            FileBasedConfiguration config = builder.getConfiguration();
            FileHandler fileHandler = new FileHandler(config);
            fileHandler.load(inputStream);
            return config;
        } catch (ConfigurationException cex) {
            throw new RuntimeException(format("cant load env config resource: %s", inputStream), cex);
        }
    }

}
