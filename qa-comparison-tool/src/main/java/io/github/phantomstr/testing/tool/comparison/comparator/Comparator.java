package io.github.phantomstr.testing.tool.comparison.comparator;

import io.github.phantomstr.testing.tool.comparison.error.handler.RuleErrorHandler;

import java.util.UUID;

public interface Comparator<SELF extends Comparator<SELF>> {

    SELF compare(String sourceEnv, String targetEnv, UUID runId);

    SELF validate();

    <R> R handle(RuleErrorHandler<R> handler);

}
