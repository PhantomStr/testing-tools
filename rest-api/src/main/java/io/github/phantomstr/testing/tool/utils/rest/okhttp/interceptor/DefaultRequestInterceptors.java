package io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("unused")
@Slf4j
@Getter
public class DefaultRequestInterceptors<SELF extends DefaultRequestInterceptors<SELF>> implements RequestInterceptors {
    @Getter(AccessLevel.NONE)
    protected final SELF myself;
    private final Properties interceptorProperties = new Properties();
    private final List<RequestInterceptor> interceptors = new ArrayList<>();

    public DefaultRequestInterceptors() {this(DefaultRequestInterceptors.class);}

    protected DefaultRequestInterceptors(Class<?> selfType) {
        //noinspection unchecked
        this.myself = (SELF) selfType.cast(this);
    }

    public static DefaultRequestInterceptors<?> loggingOnly() {
        return new DefaultRequestInterceptors<>()
                .withLoggingInConsole()
                .withLoggingInAllure();
    }

    public SELF withLoggingInAllure() {
        return addInterceptorIfAbsent(new AllureLogRequestInterceptor());
    }

    public SELF withLoggingInConsole() {
        return addInterceptorIfAbsent(new LoggingInConsoleInterceptor());
    }

    public SELF withHeader(String key, String value) {
        addInterceptorIfAbsent(new AddHeaderInterceptor(key, value));
        return myself;
    }

    public SELF addInterceptorIfAbsent(RequestInterceptor interceptor) {
        String key = interceptor.getClass().getName();
        if (interceptorProperties.get(key) == null) {
            addInterceptor(interceptor, key, 1);
        } else {
            if (interceptor.multipleInOneChain()) {
                addInterceptor(interceptor, key, ((Integer) interceptorProperties.get(key)) + 1);
            } else {
                log.warn("Попытка повторно добавить интерсептор {} без флага множественного переиспользования. Интерсептор НЕ ДОБАВЛЕН. " +
                                 "Следует переопределить метод multipleInOneChain=true или удалить попытку повторного добавления.", key);
            }
        }

        return myself;
    }

    private void addInterceptor(RequestInterceptor interceptor, String key, int value) {
        interceptorProperties.put(key, value);
        interceptors.add(interceptor);
    }

}
