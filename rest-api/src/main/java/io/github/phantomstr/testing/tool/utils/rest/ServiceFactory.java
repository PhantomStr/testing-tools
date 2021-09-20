package io.github.phantomstr.testing.tool.utils.rest;

import io.github.phantomstr.testing.tool.utils.rest.config.ConverterConfig;
import io.github.phantomstr.testing.tool.utils.rest.config.RestConfig;
import io.github.phantomstr.testing.tool.utils.rest.converter.factory.ConverterFactoryManager;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.DefaultOkHttpClientProvider;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.OkHttpClientProvider;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.OkHttpContext;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor.RequestInterceptors;
import io.github.phantomstr.testing.tool.utils.rest.retrofit.DefaultRetrofitProvider;
import io.github.phantomstr.testing.tool.utils.rest.retrofit.RetrofitProvider;
import lombok.Getter;
import retrofit2.Retrofit;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
public class ServiceFactory implements RestServiceProvider {
    private Retrofit retrofit;
    private RetrofitProvider<ConverterConfig> retrofitProvider = new DefaultRetrofitProvider();

    public ServiceFactory(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public ServiceFactory(RetrofitProvider<ConverterConfig> retrofitProvider) {
        this.retrofitProvider = retrofitProvider;
    }

    public ServiceFactory(RestConfig restConfig) {
        retrofitProvider = new DefaultRetrofitProvider().setRestConfig(restConfig);
    }

    public ServiceFactory(RestConfig restConfig, RequestInterceptors interceptors) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setOkHttpClientProvider(new DefaultOkHttpClientProvider()
                                                 .setInterceptors(interceptors));
    }

    public ServiceFactory(RestConfig restConfig, OkHttpContext context) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setOkHttpContext(context);
    }

    public ServiceFactory(RestConfig restConfig, RequestInterceptors interceptors, OkHttpContext context) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setOkHttpClientProvider(new DefaultOkHttpClientProvider()
                                                 .setInterceptors(interceptors)
                                                 .setOkHttpContext(context));
    }

    public ServiceFactory(RestConfig restConfig, ConverterFactoryManager<ConverterConfig> factoryManager) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setConverterFactoryManager(factoryManager);
    }

    public ServiceFactory(RestConfig restConfig, ConverterFactoryManager<ConverterConfig> factoryManager, OkHttpContext context) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setConverterFactoryManager(factoryManager)
                .setOkHttpContext(context);
    }

    public ServiceFactory(RestConfig restConfig, ConverterFactoryManager<ConverterConfig> factoryManager, RequestInterceptors interceptors) {
        this(restConfig, factoryManager, new DefaultOkHttpClientProvider().setInterceptors(interceptors));
    }


    public ServiceFactory(RestConfig restConfig, ConverterFactoryManager<ConverterConfig> factoryManager, RequestInterceptors interceptors, OkHttpContext context) {
        this(restConfig, factoryManager, new DefaultOkHttpClientProvider().setInterceptors(interceptors).setOkHttpContext(context));
    }

    public ServiceFactory(RestConfig restConfig, OkHttpClientProvider okHttpClientProvider) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setOkHttpClientProvider(okHttpClientProvider);
    }

    public ServiceFactory(RestConfig restConfig, ConverterFactoryManager<ConverterConfig> factoryManager, OkHttpClientProvider okHttpClientProvider) {
        retrofitProvider = new DefaultRetrofitProvider()
                .setRestConfig(restConfig)
                .setConverterFactoryManager(factoryManager)
                .setOkHttpClientProvider(okHttpClientProvider);
    }

    @Override
    public Retrofit getRetrofit() {
        return defaultIfNull(retrofit, retrofitProvider.getRetrofit());
    }

}
