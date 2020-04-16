package com.phantomstr.testing.tool.rest.retrofit;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class DefaultRetrofitProviderTest {

    @Test
    public void shouldReturnNonNullableRetrofit() {
        assertNotNull(new DefaultRetrofitProvider().getRetrofit());
    }

}