package io.github.phantomstr.testing.tool.config.utils;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trim;

public class SystemPropertiesUtils {
    public static String getOrDefault(String key, String defaultValue) {
        String systemProperty = defaultIfBlank(System.getProperty(key), System.getenv().get(key));
        return defaultIfBlank(systemProperty, defaultValue);
    }

    public static boolean getOrDefault(String key, Boolean defaultValue) {
        String systemProperty = defaultIfBlank(System.getProperty(key), System.getenv().get(key));
        if (isEmpty(systemProperty)) {
            return defaultValue;
        }
        return "true".equals(trim(systemProperty).toLowerCase());
    }
}
