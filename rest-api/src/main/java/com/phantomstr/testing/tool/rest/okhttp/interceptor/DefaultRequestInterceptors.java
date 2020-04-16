package com.phantomstr.testing.tool.rest.okhttp.interceptor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@Getter
public class DefaultRequestInterceptors<SELF extends DefaultRequestInterceptors<SELF>> implements RequestInterceptors {
    @Getter(AccessLevel.NONE)
    protected final SELF myself;
    private Properties interceptorProperties = new Properties();
    private List<RequestInterceptor> interceptors = new ArrayList<>();

    public DefaultRequestInterceptors() {
        this(DefaultRequestInterceptors.class);
    }

    protected DefaultRequestInterceptors(Class<?> selfType) {
        //noinspection unchecked
        this.myself = (SELF) selfType.cast(this);
    }

    public static DefaultRequestInterceptors loggingOnly() {
        return new DefaultRequestInterceptors()
                .withLoggingInConsole()
                .withLoggingInAllure();
    }

    public SELF withLoggingInAllure() {
        return addInterceptorIfAbsent(new AllureLogRequestInterceptor());
    }

    public SELF withLoggingInConsole() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new LogAdapter());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        addInterceptorIfAbsent(logging::intercept);
        return myself;
    }

    public SELF withHeader(String key, String value) {
        addInterceptorIfAbsent(new AddHeaderInterceptor(key, value));
        return myself;
    }

    private SELF addInterceptorIfAbsent(RequestInterceptor interceptor) {
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

    private static class LogAdapter implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String s) {
            log.info(s);
        }

    }

}
