package com.phantomstr.testing.tool.rest.config;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class DefaultRestConfig implements RestConfig {
    String baseUrl;
    String apiRoot;
}
