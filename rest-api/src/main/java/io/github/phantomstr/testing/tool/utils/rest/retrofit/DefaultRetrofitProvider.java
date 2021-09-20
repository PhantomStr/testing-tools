package io.github.phantomstr.testing.tool.utils.rest.retrofit;

import io.github.phantomstr.testing.tool.utils.rest.ApiUrl;
import io.github.phantomstr.testing.tool.utils.rest.config.ConverterConfig;
import io.github.phantomstr.testing.tool.utils.rest.config.RestConfig;
import io.github.phantomstr.testing.tool.utils.rest.converter.factory.ConverterFactoryManager;
import io.github.phantomstr.testing.tool.utils.rest.converter.factory.DefaultConverterFactoryManager;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.DefaultOkHttpClientProvider;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.OkHttpClientProvider;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.OkHttpContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Retrofit;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Setter
@Getter
public class DefaultRetrofitProvider implements RetrofitProvider<ConverterConfig> {
    protected Retrofit retrofit;
    protected ConverterFactoryManager<ConverterConfig> converterFactoryManager = new DefaultConverterFactoryManager();
    @Getter
    protected OkHttpClientProvider okHttpClientProvider = new DefaultOkHttpClientProvider();

    @Setter(AccessLevel.NONE)
    protected ApiUrl apiUrl = new ApiUrl("http://localhost:8080/");

    @Override
    public Retrofit getRetrofit() {
        return defaultIfNull(retrofit, init());
    }

    @Override
    public DefaultRetrofitProvider setRestConfig(RestConfig restConfig) {
        apiUrl = new ApiUrl(restConfig.getBaseUrl(), restConfig.getApiRoot());
        return this;
    }

    @Override
    public RetrofitProvider<ConverterConfig> setConverterFactoryManager(ConverterFactoryManager<ConverterConfig> converterFactoryManager) {
        this.converterFactoryManager = converterFactoryManager;
        return this;
    }

    @Override
    public DefaultRetrofitProvider setOkHttpClientProvider(OkHttpClientProvider okHttpClientProvider) {
        this.okHttpClientProvider = okHttpClientProvider;
        return this;
    }

    @Override
    public RetrofitProvider<ConverterConfig> setOkHttpContext(OkHttpContext context) {
        okHttpClientProvider.setOkHttpContext(context);
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
