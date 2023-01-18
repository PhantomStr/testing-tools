package io.github.phantomstr.testing.tool.utils.retofit;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.commons.lang3.ObjectUtils;
import retrofit2.Response;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class ResponseUtils {
    public static String getRequestDescription(Response<?> response) {
        return getRequestDescription(response.raw());
    }

    public static String getRequestDescription(okhttp3.Response response) {
        try {
            Request request = response.request();
            String format = "--> %s %s %s\n%s \n<-- %s \n%s";
            RequestBody requestBody = request.body();

            return format(format,
                          request.method(),
                          request.url(),
                          (isNull(requestBody) ? "" : "[" + requestBody.contentType() + "]"),
                          (isNull(requestBody) ? "" : getRequestBody(request)),
                          (response.networkResponse()),
                          cloneResponseBody(response));
        } catch (Exception e) {
            log.warn("getRequestDescription failed", e);
            return "";
        }
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = Math.min(buffer.size(), 64L);
            buffer.copyTo(prefix, 0L, byteCount);

            for (int i = 0; i < 16 && !prefix.exhausted(); ++i) {
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }

            return true;
        } catch (EOFException var6) {
            return false;
        }
    }

    private static String cloneResponseBody(okhttp3.Response response) throws IOException {
        if (nonNull(response.body()) && nonNull(response.body().source())) {
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.getBuffer();
            return buffer.clone().readString(StandardCharsets.UTF_8);
        }
        return "";
    }

    private static String getRequestBody(Request request) throws IOException {
        RequestBody requestBody = request.body();
        if (nonNull(requestBody)) {
            if (bodyEncoded(request.headers())) {
                return "(encoded body omitted)";
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                if (isPlaintext(buffer)) {
                    return buffer.readString(getCharset(requestBody));
                } else {
                    return "(binary " + requestBody.contentLength() + "-byte body omitted)";
                }
            }
        } else {
            return "";
        }
    }

    private static Charset getCharset(RequestBody requestBody) {
        Charset charset = StandardCharsets.UTF_8;
        MediaType contentType = requestBody.contentType();
        if (nonNull(contentType)) {
            charset = ObjectUtils.defaultIfNull(contentType.charset(), StandardCharsets.UTF_8);
        }
        return charset;
    }

    private static boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

}
