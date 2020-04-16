package com.phantomstr.testing.tool.rest.config;

/**
 * Интерфейс описывает класс, являющийся конфигурацией
 * для создания объекта Retrofit.
 */
public interface RestConfig {

    /**
     * Ссылка на тестируемый хост.
     * Может содержать порт.
     * URL должен начинаться с http:// или https://
     *
     * @return хост;
     */
    default String getBaseUrl() {
        return "http://localhost:8080/";
    }

    /**
     * Относительный путь к корню API.
     * Например "/api/v1/"
     *
     * @return относительный путь к корню API.
     */
    default String getApiRoot() {
        return "";
    }

}
