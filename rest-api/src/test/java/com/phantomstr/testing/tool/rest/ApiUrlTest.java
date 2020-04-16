package com.phantomstr.testing.tool.rest;

import okhttp3.HttpUrl;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ApiUrlTest {

    @Test(dataProvider = "correctUrls")
    public void shouldReturnCorrectUrl(String baseUrl, String apiRoot, String expectedUrl) {
        assertEquals(new ApiUrl(baseUrl, apiRoot).getUrl(), HttpUrl.get(expectedUrl));
    }


    @Test(dataProvider = "wrongUrls", expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException(String baseUrl, String apiRoot, String expectedUrl) {
        new ApiUrl(baseUrl, apiRoot).getUrl();
    }

    @DataProvider(name = "correctUrls")
    public static Object[][] correctUrls() {
        return new Object[][]{
                {"a", "b\\c", "http://a/b/c/"},
                {"http://a\\", "\\b\\c\\", "http://a/b/c/"},
                {"http://a\\", "/b/c/", "http://a/b/c/"},
                {"http://a/", "\\b\\c\\", "http://a/b/c/"},
                {"https://a/b/", "\\c", "https://a/b/c/"},
                {"http:\\\\a", "b\\c", "http://a/b/c/"},
                {"\\a\\b\\c\\", "", "http://a/b/c/"}
        };
    }

    @DataProvider(name = "wrongUrls")
    public static Object[][] wrongUrls() {
        return new Object[][]{
                {"", "http://a\\b\\c\\", "http://a/b/c/"},
                {"/", "http:\\\\a\\b\\c\\", "http://a/b/c/"},
                {"\\", "http\\a\\b\\c\\", "https://a/b/c/"}
        };
    }

}