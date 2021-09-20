package io.github.phantomstr.testing.tool.utils.rest.config;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class DefaultRestConfig implements RestConfig {
    String baseUrl;
    String apiRoot;

}
