package io.github.phantomstr.testing.tool.utils.rest.okhttp;

import io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor.RequestInterceptors;
import okhttp3.OkHttpClient;

/**
 * Интерфейс класса, создающего {@link OkHttpClient клиент} для
 * обмена сообщениями по HTTP протоколу.
 */
public interface OkHttpClientProvider {
    /**
     * Контекст создания клиента
     *
     * @param okHttpContext контекст
     * @return this;
     */
    OkHttpClientProvider setOkHttpContext(OkHttpContext okHttpContext);

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
