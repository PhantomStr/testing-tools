package com.phantomstr.testing.tool.config.merger;

import org.apache.commons.configuration2.Configuration;

import java.io.InputStream;

public interface MultiConfigMerger {
    Configuration computeMergedConfig(String env, String configName);

    InputStream computeHighPriorityResource(String env, String configName);
}
