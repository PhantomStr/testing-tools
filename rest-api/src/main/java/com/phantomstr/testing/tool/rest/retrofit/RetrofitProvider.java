package com.phantomstr.testing.tool.rest.retrofit;

import com.phantomstr.testing.tool.rest.config.RestConfig;
import com.phantomstr.testing.tool.rest.converter.factory.ConverterFactoryManager;
import com.phantomstr.testing.tool.rest.okhttp.OkHttpClientProvider;
import retrofit2.Retrofit;

/**
 * Интерфейс класса, поставляющего экземпляр Retrofit.
 */
public interface RetrofitProvider {

    /**
     * Получение экземпляра Retrofit.
     *
     * @return Retrofit
     */
    Retrofit getRetrofit();

    /**
     * Конфигурация для генерации экземпляра ретрофита.
     * В минимальном варианте может содержать только URL хоста.
     *
     * @param restConfig конфигурация
     * @return this
     */
    RetrofitProvider setRestConfig(RestConfig restConfig);

    /**
     * Установка {@link ConverterFactoryManager менеджера фабрик} для конвертации запросов и ответов.
     *
     * @param converterFactoryManager converterFactoryManager.
     * @return this
     */
    RetrofitProvider setConverterFactoryManager(ConverterFactoryManager converterFactoryManager);


    /**
     * Установка {@link OkHttpClientProvider поставщика реез клиентов}.
     *
     * @param okHttpClientProvider okHttpClientProvider
     * @return this
     */
    RetrofitProvider setOkHttpClientProvider(OkHttpClientProvider okHttpClientProvider);

}
