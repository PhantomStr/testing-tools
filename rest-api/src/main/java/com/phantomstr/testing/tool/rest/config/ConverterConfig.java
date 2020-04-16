package com.phantomstr.testing.tool.rest.config;

/**
 * Интерфейс описывает класс, являющийся конфигурацией
 * для конвертера сущностей при отправке и получении данных при запросе.
 */
public interface ConverterConfig {

    /**
     * Параметр отвечает за выбрасывание/игнор исключения
     * когда в ответе приходит поле, которое не содержится в модели.
     *
     * @return по умолчанию true (Выбрасывать исключение)
     */
    default boolean failOnUnknownProperties() {
        return true;
    }

    /**
     * Параметр отвечает за сериализацию полей со значением null.
     *
     * @return по умолчанию false (Не сериализировать)
     */
    default boolean serializeNullValuedProperties() {
        return false;
    }

    /**
     * Параметр отвечает за выбрасывание/игнор исключения
     * когда в ответе приходит поле описанное в модели как массив или список,
     * и при этом в значении не массив, а одиночное значение.
     * <p>
     * При значении параметра true произойдёт преобразование: {"key":"value} в {"key":["value"]}
     *
     * @return по умолчанию true (Игнорировать и обернуть объект в массив или список)
     */
    default boolean acceptSingleValueAsArray() {
        return true;
    }


    /**
     * Параметр отвечает за перенос параметров построчно с табуляцией при сереализации объектов.
     *
     * <p>
     * При значении параметра true произойдёт преобразование: {"key":"value} в \n{\r\n"key":["value"]\n}
     *
     * @return по умолчанию true
     */
    default boolean indentOutput() {
        return true;
    }

    /**
     * Параметр отвечает за десериализацию enum'ов.
     *
     * <p>
     * При значении параметра true енамы десериализируются игнорируя регистр
     *
     * @return по умолчанию true
     */
    default boolean acceptCaseInsensitiveEnums() {
        return true;
    }

}
