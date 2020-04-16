package com.phantomstr.testing.tool.utils.rest.okhttp;

import com.phantomstr.testing.tool.utils.rest.okhttp.interceptor.DefaultRequestInterceptors;
import com.phantomstr.testing.tool.utils.rest.okhttp.interceptor.RequestInterceptors;
import lombok.Getter;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;

public class DefaultOkHttpClientProvider implements OkHttpClientProvider {
    private static final int TIMEOUT_SEC = 60;
    private RequestInterceptors interceptors = DefaultRequestInterceptors.loggingOnly();
    private OkHttpClient okHttpClient;
    private boolean needSetInterceptors;
    @Getter
    private OkHttpContext okHttpContext;

    @Override
    public OkHttpClientProvider setOkHttpContext(OkHttpContext okHttpContext) {
        this.okHttpContext = okHttpContext;
        return this;
    }

    @Override
    public OkHttpClient getClient() {
        if (okHttpClient != null) {
            if (needSetInterceptors) {
                OkHttpClient.Builder builder = okHttpClient.newBuilder();
                builder.interceptors().clear();
                initInterceptors(builder);
                okHttpClient = builder.build();
                needSetInterceptors = false;
            }
        } else {
            okHttpClient = init(okHttpContext, new OkHttpClient.Builder());
        }
        return okHttpClient;
    }

    @Override
    public OkHttpClientProvider setInterceptors(RequestInterceptors interceptors) {
        this.interceptors = interceptors;
        needSetInterceptors = true;
        return this;
    }

    public OkHttpClientProvider setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    private static void initSSL(OkHttpContext context, OkHttpClient.Builder httpClientBuilder) {

        SSLContext sslContext = null;
        try {
            if (context != null) {
                sslContext = createCertificate(context.getCertificate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sslContext != null) {
            httpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), systemDefaultTrustManager());
        }

    }

    @SneakyThrows
    private static SSLContext createCertificate(InputStream trustedCertificateIS) {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try (trustedCertificateIS) {
            ca = cf.generateCertificate(trustedCertificateIS);
        }

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext;
    }

    private static X509TrustManager systemDefaultTrustManager() {

        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }

    }

    private OkHttpClient init(OkHttpContext context, OkHttpClient.Builder builder) {
        OkHttpClient.Builder okHttpClientBuilder = builder
                .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS);
        initInterceptors(okHttpClientBuilder);
        initSSL(context, okHttpClientBuilder);
        okHttpClient = okHttpClientBuilder.build();
        return okHttpClient;
    }

    private void initInterceptors(OkHttpClient.Builder okHttpClientBuilder) {
        ofNullable(interceptors)
                .map(RequestInterceptors::getInterceptors)
                .orElse(new ArrayList<>())
                .forEach(okHttpClientBuilder::addInterceptor);
    }

}
