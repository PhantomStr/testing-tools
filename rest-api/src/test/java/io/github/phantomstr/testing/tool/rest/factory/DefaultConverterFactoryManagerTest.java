package io.github.phantomstr.testing.tool.rest.factory;

import io.github.phantomstr.testing.tool.utils.rest.config.ConverterConfig;
import io.github.phantomstr.testing.tool.utils.rest.converter.factory.DefaultConverterFactoryManager;
import org.testng.annotations.Test;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static io.github.phantomstr.testing.tool.utils.rest.converter.factory.ConverterType.XML;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DefaultConverterFactoryManagerTest {

    @Test
    public void defaultFormatIsJson() {
        DefaultConverterFactoryManager factoryManager = new DefaultConverterFactoryManager();
        assert factoryManager.getConverterFactory() instanceof JacksonConverterFactory;
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void setXmlFormatShouldChangeXmlConverterFactory() {
        DefaultConverterFactoryManager factoryManager = new DefaultConverterFactoryManager();
        factoryManager.setFormat(XML).getConverterFactory();
    }

    @Test
    public void shouldContainCorrectDefaultConfigValues() {
        ConverterConfig config = new DefaultConverterFactoryManager().getConfig();
        assertTrue(config.acceptSingleValueAsArray());
        assertTrue(config.failOnUnknownProperties());
        assertFalse(config.serializeNullValuedProperties());
        assertTrue(config.indentOutput());
        assertTrue(config.acceptCaseInsensitiveEnums());
    }

}