package com.phantomstr.testing.tool.comparison.rule;

import org.xmlunit.diff.Difference;

import java.util.Collection;

public interface ValidationRules<SELF extends ValidationRules<SELF>> {
    SELF add(ValidationRule<Difference> rule);

    SELF validate(Iterable<Difference> differences);

    Collection<AssertionError> getErrors();

}
