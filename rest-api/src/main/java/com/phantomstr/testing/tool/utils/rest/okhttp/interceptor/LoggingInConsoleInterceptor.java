package com.phantomstr.testing.tool.utils.rest.okhttp.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;

public class LoggingInConsoleInterceptor extends RequestInterceptor.MultipleRequestInterceptor {

    @Getter
    private final LogAdapter adapter = new LogAdapter();


    public LoggingInConsoleInterceptor() {this(HttpLoggingInterceptor.Level.BODY);}

    public LoggingInConsoleInterceptor(HttpLoggingInterceptor.Level level) {this(level, new LogAdapter());}

    public LoggingInConsoleInterceptor(LogAdapter adapter) {this(HttpLoggingInterceptor.Level.BODY, adapter);}

    public LoggingInConsoleInterceptor(HttpLoggingInterceptor.Level level, LogAdapter adapter) {
        this(new HttpLoggingInterceptor(adapter).setLevel(level));
    }

    private LoggingInConsoleInterceptor(Interceptor requestInterceptor) {
        super(requestInterceptor);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogAdapter implements HttpLoggingInterceptor.Logger {
        @Setter
        @Getter
        private Logger log = org.slf4j.LoggerFactory.getLogger(LoggingInConsoleInterceptor.class);

        @Override
        public void log(String s) {
            getLog().info(s);
        }

    }

}
