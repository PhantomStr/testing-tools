package com.phantomstr.testing.tool.rest.okhttp;

import com.phantomstr.testing.tool.rest.okhttp.interceptor.DefaultRequestInterceptors;
import com.phantomstr.testing.tool.rest.okhttp.interceptor.RequestInterceptors;
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