package io.github.phantomstr.testing.tool.utils.rest.retrofit;

import io.github.phantomstr.testing.tool.utils.rest.config.ConverterConfig;
import io.github.phantomstr.testing.tool.utils.rest.config.RestConfig;
import io.github.phantomstr.testing.tool.utils.rest.converter.factory.ConverterFactoryManager;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.OkHttpClientProvider;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.OkHttpContext;
import retrofit2.Retrofit;

/**
 * Интерфейс класса, поставляющего экземпляр Retrofit.
 */
public interface RetrofitProvider<C extends ConverterConfig> {

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
    RetrofitProvider<C> setRestConfig(RestConfig restConfig);

    /**
     * Установка {@link ConverterFactoryManager менеджера фабрик} для конвертации запросов и ответов.
     *
     * @param converterFactoryManager converterFactoryManager.
     * @return this
     */
    RetrofitProvider<C> setConverterFactoryManager(ConverterFactoryManager<C> converterFactoryManager);

    OkHttpClientProvider getOkHttpClientProvider();

    /**
     * Установка {@link OkHttpClientProvider поставщика реез клиентов}.
     *
     * @param okHttpClientProvider okHttpClientProvider
     * @return this
     */
    RetrofitProvider<C> setOkHttpClientProvider(OkHttpClientProvider okHttpClientProvider);

    RetrofitProvider<C> setOkHttpContext(OkHttpContext context);


}
