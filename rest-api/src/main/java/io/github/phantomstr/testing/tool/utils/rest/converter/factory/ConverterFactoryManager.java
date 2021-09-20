package io.github.phantomstr.testing.tool.utils.rest.converter.factory;

import io.github.phantomstr.testing.tool.utils.rest.config.ConverterConfig;
import retrofit2.Converter;

/**
 * Интерфейс класса, отвечающего за настройку и поставку нужной
 * {@link Converter.Factory фабрики конвертеров}
 * сущностей при отправке и получении данных.
 */
public interface ConverterFactoryManager<T extends ConverterConfig> {

    /**
     * Получение {@link Converter.Factory фабрики конвертеров} в модели для запросов и ответов.
     *
     * @return фабрика конвертеров
     */
    Converter.Factory getConverterFactory();

    /**
     * Задаёт {@link ConverterType тип конвертера}.
     *
     * @param converterType формат сообщения для конвертации.
     * @return this
     */
    ConverterFactoryManager setFormat(ConverterType converterType);

    /**
     * Задаёт {@link ConverterConfig настройки конвертера}.
     *
     * @param config класс конфигурацииж
     * @return this
     */
    ConverterFactoryManager setConfig(T config);

}
