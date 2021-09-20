package io.github.phantomstr.testing.tool.rest;

import io.github.phantomstr.testing.tool.utils.rest.ServiceFactory;
import io.github.phantomstr.testing.tool.utils.rest.config.DefaultRestConfig;
import io.github.phantomstr.testing.tool.utils.rest.config.RestConfig;
import io.github.phantomstr.testing.tool.utils.rest.converter.factory.ConverterType;
import io.github.phantomstr.testing.tool.utils.rest.converter.factory.DefaultConverterFactoryManager;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.DefaultOkHttpClientProvider;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.OkHttpContext;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor.DefaultRequestInterceptors;
import io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor.RequestInterceptors;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.io.InputStream;

import static io.github.phantomstr.testing.tool.utils.rest.okhttp.interceptor.DefaultRequestInterceptors.loggingOnly;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;


public class ServiceFactoryTest {
    private static final String TEST_URL = "https://ru.wikipedia.org/";
    private static final String TEST_API_ROOT = "static/images/";
    private final String fileName = "ruwiki-1.5x.png";
    private final RestConfig config = new DefaultRestConfig(TEST_URL, TEST_API_ROOT);

    @Test
    public void shouldExecuteServiceMethodWithDefaultRetrofitFactory() throws IOException {
        // given
        int expectedSize = 13931;
        Call<ResponseBody> emoticon = new ServiceFactory(config).getService(IconsService.class).getEmoticon(fileName);

        // when
        Response<ResponseBody> response = emoticon.execute();

        // then
        ResponseBody body = response.body();
        assertNotNull(body);
        byte[] actual = body.bytes();
        assertEquals("Non nullable response", expectedSize, actual.length);
    }

    @Test
    public void shouldCreateServiceWithCustomRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(TEST_URL).build();
        assertEquals(new ServiceFactory(retrofit).getRetrofit(), retrofit);
    }

    @Test
    public void testServiceFactoryCreatedWithRestConfig() throws IOException {
        assert new ServiceFactory(config).getService(IconsService.class).getEmoticon(fileName).execute().isSuccessful();
    }

    @Test
    public void testServiceFactoryRetrofitWithInterceptors() throws IOException {
        String testText = "HELLO_INTERCEPTORS";
        RequestInterceptors requestInterceptors = new DefaultRequestInterceptors();
        requestInterceptors.getInterceptors()
                .add(chain -> new okhttp3.Response.Builder()
                        .request(chain.request())
                        .protocol(Protocol.HTTP_1_0)
                        .code(200)
                        .message(testText)
                        .body(ResponseBody.create(MediaType.get("text/plain"), testText))
                        .build());

        Response<ResponseBody> response = new ServiceFactory(config, requestInterceptors)
                .getService(IconsService.class)
                .getEmoticon(fileName)
                .execute();

        assert response.message().equals(testText);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testServiceFactoryRetrofitWithFactory() throws IOException {
        new ServiceFactory(config,
                           new DefaultConverterFactoryManager()
                                   .setFormat(ConverterType.XML),
                           loggingOnly())
                .getService(IconsService.class)
                .getEmoticon(fileName)
                .execute();
    }

    @Test
    public void testCreateServiceFactoryWithCertificate() {
        OkHttpContext context = new OkHttpContext() {
            @Override
            public InputStream getCertificate() {
                return ServiceFactoryTest.class
                        .getClassLoader()
                        .getResourceAsStream("youtube.cer");
            }
        };

        ServiceFactory serviceFactory = new ServiceFactory(config, context);
        assertNotNull(((DefaultOkHttpClientProvider) serviceFactory.getRetrofitProvider().getOkHttpClientProvider())
                              .getOkHttpContext().getCertificate());
    }

    public interface IconsService {
        @GET("project-logos/{filename}")
        Call<ResponseBody> getEmoticon(@Path("filename") String fileName);

    }

}