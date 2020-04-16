package com.phantomstr.testing.tool.utils.rest.okhttp.interceptor;

import com.phantomstr.testing.tool.utils.rest.okhttp.interceptor.RequestInterceptor.SingletonRequestInterceptor;
import io.qameta.allure.Attachment;
import okhttp3.Interceptor;
import okhttp3.Response;

import static com.phantomstr.testing.tool.utils.retofit.ResponseUtils.getRequestDescription;

public class AllureLogRequestInterceptor extends SingletonRequestInterceptor {

    public AllureLogRequestInterceptor() {
        this(chain -> {
            Response response = chain.proceed(chain.request());
            logRequestInfo(getRequestDescription(response));
            return response;
        });
    }

    private AllureLogRequestInterceptor(Interceptor requestInterceptor) {
        super(requestInterceptor);
    }

    @Attachment(value = "request", type = "application/json", fileExtension = ".log")
    private static String logRequestInfo(String info) {
        return info;
    }

}
