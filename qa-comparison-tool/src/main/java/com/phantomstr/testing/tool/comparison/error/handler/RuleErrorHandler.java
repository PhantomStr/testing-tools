package com.phantomstr.testing.tool.comparison.error.handler;

import java.util.Collection;

public interface RuleErrorHandler<R> {
    R handle(Collection<AssertionError> errors);

}
