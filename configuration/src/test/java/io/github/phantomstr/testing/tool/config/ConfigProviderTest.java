package io.github.phantomstr.testing.tool.config;

import org.apache.commons.configuration2.Configuration;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

public class ConfigProviderTest {
    private static final String MERGED_CONFIG_NAME = "merge.properties";
    private static final String OVERRIDES_CONFIG_NAME = "override.properties";
    private static final String TEST = "TEST";
    private static final String OTHER_CONFIG = "other.config";

    @Test
    public void mergeConfiguration() {
        Configuration configuration = ConfigProvider.getConfiguration(TEST, MERGED_CONFIG_NAME);
        assertEquals(configuration.getInt("key1"), 1);
        assertEquals(configuration.getInt("key2"), 2);
        assertEquals(configuration.getInt("key3"), 3);
        assertEquals(configuration.getInt("key4"), 4);
    }

    @Test
    public void overrideConfiguration() {
        Configuration configuration = ConfigProvider.getConfiguration(TEST, OVERRIDES_CONFIG_NAME);
        assertEquals(configuration.getString("key1"), "root");
        assertEquals(configuration.getString("key2"), "envRoot");
        assertEquals(configuration.getString("key3"), "env");
    }

    @Test
    public void getConfig() {
        Configuration configuration = ConfigProvider.getConfiguration(TEST, OTHER_CONFIG);
        assertEquals(configuration.getInt("int_value"), 123);
        assertEquals(configuration.getString("string_value"), "1x:!@#$%^&*(\"");
        assertTrue(configuration.getBoolean("boolean_value"));
        assertFalse(configuration.getBoolean("boolean_value2"));
    }
}