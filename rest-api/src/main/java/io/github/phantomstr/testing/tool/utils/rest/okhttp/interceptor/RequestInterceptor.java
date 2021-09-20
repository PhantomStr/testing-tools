package io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * Интерфейс класса, отвечающего за поставку дополнительных {@link Interceptor обработчиков запросов}.
 */
public interface RequestInterceptor extends Interceptor {

    /**
     * Флаг отвечает за то, можно ли использовать в одной цепочке обработки реквеста несколько интерсепторов одного класса.
     * разрешено более одного если true (default=true);
     *
     * @return boolean value.
     */
    default boolean multipleInOneChain() {return true;}

    abstract class SingletonRequestInterceptor implements RequestInterceptor {
        private final Interceptor requestInterceptor;

        public SingletonRequestInterceptor(Interceptor requestInterceptor) {
            this.requestInterceptor = requestInterceptor;
        }

        @Override
        public boolean multipleInOneChain() {
            return false;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            return this.requestInterceptor.intercept(chain);
        }

    }

    abstract class MultipleRequestInterceptor implements RequestInterceptor {
        private final Interceptor requestInterceptor;

        public MultipleRequestInterceptor(Interceptor requestInterceptor) {
            this.requestInterceptor = requestInterceptor;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            return this.requestInterceptor.intercept(chain);
        }

        @Override
        public boolean multipleInOneChain() {
            return true;
        }

    }

}
