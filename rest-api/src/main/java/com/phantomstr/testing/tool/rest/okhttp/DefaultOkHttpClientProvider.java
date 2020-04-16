package com.phantomstr.testing.tool.rest.okhttp;

import com.phantomstr.testing.tool.rest.okhttp.interceptor.DefaultRequestInterceptors;
import com.phantomstr.testing.tool.rest.okhttp.interceptor.RequestInterceptors;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class DefaultOkHttpClientProvider implements OkHttpClientProvider {
    private static final int TIMEOUT_SEC = 60;
    private RequestInterceptors interceptors = DefaultRequestInterceptors.loggingOnly();
    private OkHttpClient okHttpClient;

    @Override
    public OkHttpClient getClient() {
        return defaultIfNull(okHttpClient, init());
    }

    @Override
    public OkHttpClientProvider setInterceptors(RequestInterceptors interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public DefaultOkHttpClientProvider setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    private OkHttpClient init() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS);
        ofNullable(interceptors)
                .map(RequestInterceptors::getInterceptors)
                .orElse(new ArrayList<>())
                .forEach(okHttpClientBuilder::addInterceptor);
        okHttpClient = okHttpClientBuilder.build();
        return okHttpClient;
    }


}
