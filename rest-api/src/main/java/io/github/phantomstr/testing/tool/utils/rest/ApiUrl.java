package io.github.phantomstr.testing.tool.utils.rest;

import lombok.NoArgsConstructor;
import okhttp3.HttpUrl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.strip;

@NoArgsConstructor

public class ApiUrl {
    private String baseUrl = "";
    private String apiRoot = "";

    public ApiUrl(String baseUrl, String apiRoot) {
        this.baseUrl = baseUrl;
        this.apiRoot = apiRoot;
    }

    public ApiUrl(String baseUrl) {
        this(baseUrl, "");
    }

    public HttpUrl getUrl() {
        baseUrl = baseUrl.replaceAll("\\\\", "/");
        apiRoot = apiRoot.replaceAll("\\\\", "/");
        String slashes = "/\\";

        String host = strip(baseUrl, slashes);
        if (!host.startsWith("http")) {
            host = "http://" + baseUrl;
        }

        String path = strip(apiRoot, slashes);
        Matcher matcher = Pattern.compile("(http[s]?)[^/\\\\]*(.*)").matcher(path);
        if (matcher.find()) {
            throw new IllegalArgumentException("api root must be relative");
        }
        String stripped = strip(host + "/" + path, slashes) + "/";

        try {
            URL url = new URL(stripped);
            return HttpUrl.get(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return getUrl().toString();
    }

}
