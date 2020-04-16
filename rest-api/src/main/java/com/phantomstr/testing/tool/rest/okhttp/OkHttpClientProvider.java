package com.phantomstr.testing.tool.rest.okhttp;

import com.phantomstr.testing.tool.rest.okhttp.interceptor.RequestInterceptors;
import okhttp3.OkHttpClient;

/**
 * Интерфейс класса, создающего {@link OkHttpClient клиент} для
 * обмена сообщениями по HTTP протоколу.
 */
public interface OkHttpClientProvider {

    /**
     * получение {@link OkHttpClient клиента} для обмена по HTTP.
     *
     * @return OkHttpClient.
     */
    OkHttpClient getClient();

    /**
     * Установка своих дополнительных {@link RequestInterceptors обработчиков} для сообщений перед отправкой и после получения.
     *
     * @param interceptors обработчики сообщений.
     * @return this
     */
    OkHttpClientProvider setInterceptors(RequestInterceptors interceptors);
}
