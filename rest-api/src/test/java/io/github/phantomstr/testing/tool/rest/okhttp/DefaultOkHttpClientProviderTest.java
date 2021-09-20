package io.github.phantomstr.testing.tool.rest.okhttp;

import io.github.phantomstr.testing.tool.utils.rest.okhttp.DefaultOkHttpClientProvider;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.OkHttpClientProvider;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor.DefaultRequestInterceptors;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor.RequestInterceptors;
import okhttp3.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class DefaultOkHttpClientProviderTest {

    @Test
    public void shouldReturnNonNullableClient() {
        assertNotNull(new DefaultOkHttpClientProvider().getClient());
    }

    @Test
    public void shouldAddAllInterceptors() {
        RequestInterceptors requestInterceptors = new DefaultRequestInterceptors();
        requestInterceptors.getInterceptors()
                .add(chain -> new Response.Builder().build());
        assert requestInterceptors.getInterceptors().size() > 0;

        OkHttpClientProvider provider = new DefaultOkHttpClientProvider().setInterceptors(requestInterceptors);
        assert provider.getClient().interceptors().containsAll(requestInterceptors.getInterceptors());
    }

}