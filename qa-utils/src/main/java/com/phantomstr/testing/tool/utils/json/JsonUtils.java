package com.phantomstr.testing.tool.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    private static ObjectMapper defaultMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
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

    /**
     * Return indented json string.<br>
     * throws JsonProcessingException when object.toString() not valid json
     *
     * @param validJsonWhenString object.toString() most return valid json string
     * @return indented json string
     */
    @SneakyThrows
    public static String prettyFormat(Object validJsonWhenString) {
        return defaultMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(validJsonWhenString);
    }


    /**
     * Return json string from POJO;
     *
     * @param pojo serializable object
     * @return json string
     */
    @SneakyThrows
    public static String pojoToJson(Object pojo) {
        return pojoToJson(pojo, false);
    }

    /**
     * Return json string from POJO;
     *
     * @param pojo        serializable object
     * @param prettyPrint indented if true
     * @return json string
     */
    @SneakyThrows
    public static String pojoToJson(Object pojo, boolean prettyPrint) {
        return pojoToJson(pojo, defaultMapper, prettyPrint);
    }

    /**
     * Return json string from POJO;
     *
     * @param pojo        serializable object
     * @param prettyPrint indented if true
     * @return json string
     */
    @SneakyThrows
    public static String pojoToJson(Object pojo, ObjectMapper objectMapper, boolean prettyPrint) {
        String asString = objectMapper.writeValueAsString(pojo);
        return prettyPrint ? prettyFormat(asString) : asString;
    }

    public static <T> T tryParse(Class<T> clazz, String jsonString) {
        try {
            return defaultMapper.readValue(jsonString, clazz);
        } catch (Throwable e) {
            log.warn("can't parse to {}:\n{}", clazz.getName(), jsonString);
        }
        return null;
    }

    public static <T> T tryParse(TypeReference<T> typeReference, String jsonString) {
        try {
            return defaultMapper.readValue(jsonString, typeReference);
        } catch (Throwable e) {
            log.warn("can't parse to type reference {}:\n{}", typeReference.getType().getTypeName(), jsonString);
        }
        return null;
    }
}
