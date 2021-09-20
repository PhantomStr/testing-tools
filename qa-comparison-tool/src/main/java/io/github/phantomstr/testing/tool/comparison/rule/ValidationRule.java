package io.github.phantomstr.testing.tool.comparison.rule;

import org.xmlunit.diff.Difference;

import java.util.Optional;


public interface ValidationRule<D extends Difference> {
    Optional<? extends AssertionError> validate(D difference);

    boolean isApplicable(D difference);

}
