package io.github.phantomstr.testing.tool.rest.retrofit;

import io.github.phantomstr.testing.tool.utils.rest.retrofit.DefaultRetrofitProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class DefaultRetrofitProviderTest {

    @Test
    public void shouldReturnNonNullableRetrofit() {
        assertNotNull(new DefaultRetrofitProvider().getRetrofit());
    }

}