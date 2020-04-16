package com.phantomstr.testing.tool.config.pathresolver;

import java.io.InputStream;

public interface ConfigPathResolver {
    String CONFIG_ENV_ROOT_KEY = "config.env.root";

    InputStream resolveRoot(String configName);

    InputStream resolveEnvironment(String env, String configName);

    InputStream resolveGeneralConfig(String configName);

    InputStream resolveByRelativePath(String relativePath);
}
