package io.github.phantomstr.testing.tool.config.pathresolver;

import io.github.phantomstr.testing.tool.config.core.ConfigLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import static io.github.phantomstr.testing.tool.config.DefaultConfiguration.getDefaultValue;
import static io.github.phantomstr.testing.tool.config.utils.SystemPropertiesUtils.getOrDefault;
import static org.apache.commons.lang3.StringUtils.stripEnd;

@Slf4j
public class ConfigPathResolverImpl implements ConfigPathResolver {
    private static final String configRoot;

    static {
        configRoot = getOrDefault(CONFIG_ENV_ROOT_KEY, getDefaultValue(CONFIG_ENV_ROOT_KEY));
        log.info("{}: {}", CONFIG_ENV_ROOT_KEY, configRoot);
    }

    @Override
    public InputStream resolveRoot(String configName) {
        return getResource(configName);
    }

    @Override
    public InputStream resolveGeneralConfig(String configName) {
        return getConfigRootResource(configName);
    }

    @Override
    public InputStream resolveEnvironment(String environment, String configName) {
        return getEnvironmentResource(environment, configName);
    }

    @Override
    public InputStream resolveByRelativePath(String relativePath) {
        return getResource(relativePath);
    }

    private InputStream getConfigRootResource(String configFileName) {
        return getResource("env/" + configFileName);
    }

    private InputStream getEnvironmentResource(String environment, String configFileName) {
        return getResource("env/" + environment + "/" + configFileName);
    }

    private InputStream getResource(String name) {
        File file = getConfigRoot();
        return file.isAbsolute() ? getAbsolutePathResource(name) : getClasspathResource(name);
    }

    private File getConfigRoot() {
        return new File(configRoot);
    }

    @SneakyThrows
    private InputStream getAbsolutePathResource(String name) {
        File root = getConfigRoot();
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                String windowsPath = ("file:/" + root + "/").replace("\\", "/");
                return new URI(windowsPath).resolve(name).toURL().openStream();
            } else {
                URL url = root.toURI().resolve(name).toURL();
                log.debug("Resource path for {} is {}", name, url);
                try {
                    return url.openStream();
                } catch (FileNotFoundException e) {
                    return null;
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private InputStream getClasspathResource(String resourceRelativePath) {
        log.debug("try get resource in resources");
        return ConfigLoader.class.getClassLoader().getResourceAsStream(getRootRelativePath(resourceRelativePath));
    }

    private String getRootRelativePath(String resourceRelativePath) {
        String path = stripEnd(configRoot, "\\/").concat("/").concat(resourceRelativePath);
        log.debug("relative path: {}", path);
        return path;
    }
}
