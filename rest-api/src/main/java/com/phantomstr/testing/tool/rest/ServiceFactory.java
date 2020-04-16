package com.phantomstr.testing.tool.rest;

import com.phantomstr.testing.tool.rest.config.RestConfig;
import com.phantomstr.testing.tool.rest.converter.factory.ConverterFactoryManager;
import com.phantomstr.testing.tool.rest.okhttp.DefaultOkHttpClientProvider;
import com.phantomstr.testing.tool.rest.okhttp.OkHttpClientProvider;
import com.phantomstr.testing.tool.rest.okhttp.interceptor.RequestInterceptors;
import com.phantomstr.testing.tool.rest.retrofit.DefaultRetrofitProvider;
import com.phantomstr.testing.tool.rest.retrofit.RetrofitProvider;
import lombok.Getter;
import retrofit2.Retrofit;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
public class ServiceFactory implements RestServiceProvider {
    private Retrofit retrofit;
    private RetrofitProvider retrofitProvider = new DefaultRetrofitProvider();

    @Override
    public Retrofit getRetrofit() {
        return defaultIfNull(retrofit, retrofitProvider.getRetrofit());
    }

    public ServiceFactory(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public ServiceFactory(RetrofitProvider retrofitProvider) {
        this.retrofitProvider = retrofitProvider;
    }

    public ServiceFactory(RestConfig restConfig) {
        retrofitProvider = new DefaultRetrofitProvider().setRestConfig(restConfig);
    }

    public ServiceFactory(RestConfig restConfig, RequestInterceptors interceptors) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setOkHttpClientProvider(new DefaultOkHttpClientProvider().setInterceptors(interceptors));
    }

    public ServiceFactory(RestConfig restConfig, ConverterFactoryManager factoryManager) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setConverterFactoryManager(factoryManager);
    }

    public ServiceFactory(RestConfig restConfig, ConverterFactoryManager factoryManager, RequestInterceptors interceptors) {
        this(restConfig, factoryManager, new DefaultOkHttpClientProvider().setInterceptors(interceptors));
    }

    public ServiceFactory(RestConfig restConfig, OkHttpClientProvider okHttpClientProvider) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setOkHttpClientProvider(okHttpClientProvider);
    }

    public ServiceFactory(RestConfig restConfig, ConverterFactoryManager factoryManager, OkHttpClientProvider okHttpClientProvider) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setConverterFactoryManager(factoryManager)
                .setOkHttpClientProvider(okHttpClientProvider);
    }

}
