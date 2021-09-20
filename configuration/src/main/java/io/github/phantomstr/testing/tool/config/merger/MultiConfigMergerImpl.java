package io.github.phantomstr.testing.tool.config.merger;

import io.github.phantomstr.testing.tool.config.core.ConfigLoader;
import io.github.phantomstr.testing.tool.config.pathresolver.ConfigPathResolver;
import io.github.phantomstr.testing.tool.config.utils.SystemPropertiesUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.sync.LockMode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@AllArgsConstructor
public class MultiConfigMergerImpl implements MultiConfigMerger {
    private ConfigPathResolver configPathResolver;

    @Override
    public Configuration computeMergedConfig(String env, String configName) {
        InputStream rootConfigUrl = configPathResolver.resolveRoot(configName);
        InputStream envRootConfigUrl = configPathResolver.resolveGeneralConfig(configName);
        InputStream envConfigUrl = configPathResolver.resolveEnvironment(env, configName);

        if (isNull(envConfigUrl)) {
            if (isNull(envRootConfigUrl)) {
                if (isNull(rootConfigUrl)) {
                    log.warn("Can't find any configuration file {}", configName);
                    return new PropertiesConfiguration();
                }
            }
        }

        Configuration config = null;
        if (nonNull(rootConfigUrl)) {
            config = ConfigLoader.getConfig(rootConfigUrl);
        }
        config = mergeConfigurations(config, envRootConfigUrl);
        config = mergeConfigurations(config, envConfigUrl);
        return overrideIfHasSystemProperty(config);
    }

    @Override
    public InputStream computeHighPriorityResource(String env, String configName) {
        InputStream resource = configPathResolver.resolveEnvironment(env, configName);
        if (resource != null) {
            return resource;
        }
        resource = configPathResolver.resolveGeneralConfig(configName);
        if (resource != null) {
            return resource;
        }
        resource = configPathResolver.resolveRoot(configName);

        if (resource != null) {
            return resource;
        }
        log.warn("Can't find any resource {}", configName);
        return null;
    }

    private Configuration overrideIfHasSystemProperty(Configuration config) {
        config.lock(LockMode.WRITE);
        try {
            List<String> keys = new ArrayList<>();
            config.getKeys().forEachRemaining(keys::add);
            keys.forEach(k -> {
                        String options = SystemPropertiesUtils.getOrDefault(k, (String) null);
                        if (options != null) {
                            config.clearProperty(k);
                            config.addProperty(k, options);
                        }
                    }
            );
        } finally {
            config.unlock(LockMode.WRITE);
        }
        return config;
    }

    private Configuration mergeConfigurations(Configuration consumerConfiguration, InputStream highPriority) {
        if (nonNull(highPriority)) {
            Configuration highPriorityConfig = ConfigLoader.getConfig(highPriority);
            Configuration finalConsumerConfiguration = consumerConfiguration;
            if (isNull(consumerConfiguration)) {
                consumerConfiguration = highPriorityConfig;
            } else {
                highPriorityConfig.getKeys().forEachRemaining(key -> replaceProperty(highPriorityConfig, finalConsumerConfiguration, key));
                consumerConfiguration = finalConsumerConfiguration;
            }
        }
        return consumerConfiguration;
    }

    private void replaceProperty(Configuration highPriorityConfig, Configuration configuration, String key) {
        if (configuration.containsKey(key)) {
            Object highPriorityValue = highPriorityConfig.getProperty(key);
            Object lowPriorityValue = configuration.getProperty(key);
            log.info("key {} overridden: '{}' -> '{}'", key, lowPriorityValue, highPriorityValue);
            configuration.clearProperty(key);
        }
        configuration.addProperty(key, highPriorityConfig.getProperty(key));
    }

}
