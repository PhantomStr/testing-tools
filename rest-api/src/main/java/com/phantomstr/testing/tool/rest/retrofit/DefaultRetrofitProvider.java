package com.phantomstr.testing.tool.rest.retrofit;

import com.phantomstr.testing.tool.rest.ApiUrl;
import com.phantomstr.testing.tool.rest.config.RestConfig;
import com.phantomstr.testing.tool.rest.converter.factory.ConverterFactoryManager;
import com.phantomstr.testing.tool.rest.converter.factory.DefaultConverterFactoryManager;
import com.phantomstr.testing.tool.rest.okhttp.DefaultOkHttpClientProvider;
import com.phantomstr.testing.tool.rest.okhttp.OkHttpClientProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Retrofit;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Setter
@Getter
public class DefaultRetrofitProvider implements RetrofitProvider {
    protected Retrofit retrofit;
    protected ConverterFactoryManager converterFactoryManager = new DefaultConverterFactoryManager();
    protected OkHttpClientProvider okHttpClientProvider = new DefaultOkHttpClientProvider();

    @Setter(AccessLevel.NONE)
    protected ApiUrl apiUrl = new ApiUrl("http://localhost:8080/");

    @Override
    public DefaultRetrofitProvider setRestConfig(RestConfig restConfig) {
        apiUrl = new ApiUrl(restConfig.getBaseUrl(), restConfig.getApiRoot());
        return this;
    }

    @Override
    public Retrofit getRetrofit() {
        return defaultIfNull(retrofit, init());
    }

    @Override
    public DefaultRetrofitProvider setConverterFactoryManager(ConverterFactoryManager converterFactoryManager) {
        this.converterFactoryManager = converterFactoryManager;
        return this;
    }

    @Override
    public DefaultRetrofitProvider setOkHttpClientProvider(OkHttpClientProvider okHttpClientProvider) {
        this.okHttpClientProvider = okHttpClientProvider;
        return this;
    }

    private Retrofit init() {
        return new Retrofit.Builder()
                .baseUrl(apiUrl.getUrl())
                .addConverterFactory(converterFactoryManager.getConverterFactory())
                .client(okHttpClientProvider.getClient())
                .build();
    }
}
