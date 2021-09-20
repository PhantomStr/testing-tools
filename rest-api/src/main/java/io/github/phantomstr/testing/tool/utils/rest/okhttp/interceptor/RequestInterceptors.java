package io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor;

import okhttp3.Interceptor;

import java.util.List;
import java.util.Properties;

/**
 * Интерфейс класса, отвечающего за поставку дополнительных {@link Interceptor обработчиков запросов}.
 */
public interface RequestInterceptors {

    /**
     * Получение списка {@link Interceptor дополнительных обработчиков}.
     * Применяются в прямом порядке хранения.
     *
     * @param <I> RequestInterceptor.
     * @return обработчики запросов.
     */
    <I extends RequestInterceptor> List<I> getInterceptors();

    /**
     * Получение свойств для настройки дополнительных обработчиков.
     * Можно использовать как общий контекст для получения полной картины обработки запросов.
     *
     * @return Properties.
     */
    Properties getInterceptorProperties();

}
