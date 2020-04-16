package com.phantomstr.testing.tool.config.core;

import com.phantomstr.testing.tool.config.merger.MultiConfigMerger;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

@Slf4j
@AllArgsConstructor
public class CachedConfigProvider {
    private static final Map<CacheKey, Configuration> CONFIG_CACHE = new ConcurrentHashMap<>();
    private static final Map<CacheKey, byte[]> RESOURCE_CACHE = new ConcurrentHashMap<>();
    private MultiConfigMerger multiConfigMerger;

    public static void forceReloadAll() {
        CONFIG_CACHE.clear();
        RESOURCE_CACHE.clear();
    }

    public Configuration getConfiguration(String env, String configName) {
        return CONFIG_CACHE.computeIfAbsent(new CacheKey(env, configName), s -> multiConfigMerger.computeMergedConfig(env, configName));
    }

    public InputStream getResource(String env, String configName) {
        return new ByteArrayInputStream(requireNonNull(RESOURCE_CACHE.computeIfAbsent(
                new CacheKey(env, configName), s -> Optional
                        .ofNullable(multiConfigMerger.computeHighPriorityResource(env, configName))
                        .map(i -> {
                            try {
                                return i.readAllBytes();
                            } catch (IOException e) {
                                log.error("Can't read config \"" + configName + "\" for environment " + env, e);
                                return new byte[]{};
                            }
                        }).orElse(new byte[]{}))));
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    private static class CacheKey {
        String env;
        String cacheFileName;
    }
}
