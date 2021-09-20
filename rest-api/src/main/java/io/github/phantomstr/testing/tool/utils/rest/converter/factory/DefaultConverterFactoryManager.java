package io.github.phantomstr.testing.tool.utils.rest.converter.factory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.phantomstr.testing.tool.utils.rest.config.ConverterConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Converter;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.UnknownFormatConversionException;


@AllArgsConstructor
public class DefaultConverterFactoryManager implements ConverterFactoryManager<ConverterConfig> {

    @Getter
    private ConverterConfig config;
    private ConverterType converterType = ConverterType.JSON;

    public DefaultConverterFactoryManager() {
        config = new ConverterConfig() {};
    }

    @Override
    public Converter.Factory getConverterFactory() {
        switch (converterType) {
            case JSON:
                return getJsonConverterFactory();
            case XML:
                return getXmlConverterFactory();
            default:
                throw new UnknownFormatConversionException(String.valueOf(converterType));
        }
    }

    @Override
    public ConverterFactoryManager<ConverterConfig> setFormat(ConverterType converterType) {
        this.converterType = converterType;
        return this;
    }

    @Override
    public ConverterFactoryManager<ConverterConfig> setConfig(ConverterConfig config) {
        this.config = config;
        return this;
    }

    private Converter.Factory getXmlConverterFactory() {
        throw new UnsupportedOperationException("XML format converter not implemented yet.");
    }

    private Converter.Factory getJsonConverterFactory() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
                    // this code provide jackson's deserializer to use lombok @Builder annotation
                    @Override
                    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
                        if (ac.hasAnnotation(JsonPOJOBuilder.class)) {
                            return super.findPOJOBuilderConfig(ac);
                        }
                        return new JsonPOJOBuilder.Value("build", "");
                    }
                });
        if (!config.failOnUnknownProperties()) {
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        if (!config.serializeNullValuedProperties()) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        if (config.acceptSingleValueAsArray()) {
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        }
        if (config.indentOutput()) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        if (config.acceptCaseInsensitiveEnums()) {
            objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        }
        return JacksonConverterFactory.create(objectMapper);
    }

}
