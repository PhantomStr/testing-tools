package io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor;

import okhttp3.Interceptor;

public class AddHeaderInterceptor extends RequestInterceptor.MultipleRequestInterceptor {
    public AddHeaderInterceptor(String key, String value) {
        this(chain -> chain.proceed(
                chain.request()
                        .newBuilder()
                        .addHeader(key, value)
                        .build()));
    }

    private AddHeaderInterceptor(Interceptor requestInterceptor) {
        super(requestInterceptor);
    }

}
