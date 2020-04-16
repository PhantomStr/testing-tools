package com.phantomstr.testing.tool.config;

import com.phantomstr.testing.tool.config.core.CachedConfigProvider;
import com.phantomstr.testing.tool.config.merger.MultiConfigMergerImpl;
import com.phantomstr.testing.tool.config.pathresolver.ConfigPathResolverImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;

import java.io.InputStream;

import static com.phantomstr.testing.tool.config.utils.SystemPropertiesUtils.getOrDefault;

@Slf4j
public class ConfigProvider {
    public static final String ENV_NAME_KEY = "env.name";
    public static final String envName;

    private static CachedConfigProvider cachedConfigProvider;

    static {
        envName = getOrDefault(ENV_NAME_KEY, DefaultConfiguration.getDefaultValue(ENV_NAME_KEY));
        log.info("{}: {}", ENV_NAME_KEY, envName);
        cachedConfigProvider = new CachedConfigProvider(new MultiConfigMergerImpl(new ConfigPathResolverImpl()));
    }

    public static Configuration getConfiguration(String configName) {
        return getConfiguration(getEnvName(), configName);
    }

    public static Configuration getConfiguration(String env, String configName) {
        return cachedConfigProvider.getConfiguration(env, configName);
    }

    public static InputStream getResource(String name) {
        return getResource(getEnvName(), name);
    }

    public static InputStream getResource(String env, String name) {
        return cachedConfigProvider.getResource(env, name);
    }

    public static String getEnvName() {
        return envName;
    }
}
