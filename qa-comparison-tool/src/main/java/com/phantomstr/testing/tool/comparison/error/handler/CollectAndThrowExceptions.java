package com.phantomstr.testing.tool.comparison.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.repeat;

@Slf4j
public class CollectAndThrowExceptions implements RuleErrorHandler<Void> {
    @Override
    public Void handle(Collection<AssertionError> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        String message = list.stream()
                .map(AssertionError::getMessage)
                .collect(Collectors.joining("\n\t",
                                            format("\n%s\nFound errors:\n\t", repeat("-", 80)),
                                            format("\n%s", repeat("-", 80))));
        throw new AssertionError(message);
    }

}
