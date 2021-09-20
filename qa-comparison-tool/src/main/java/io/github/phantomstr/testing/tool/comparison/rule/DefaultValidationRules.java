package io.github.phantomstr.testing.tool.comparison.rule;

import org.xmlunit.diff.Difference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultValidationRules implements ValidationRules<DefaultValidationRules> {
    List<ValidationRule<Difference>> rules = new ArrayList<>();
    private List<AssertionError> errors;
    private final AllTrowAssertion allTrowAssertion;

    public DefaultValidationRules() {
        allTrowAssertion = new AllTrowAssertion();
        rules.add(allTrowAssertion);
    }

    @Override
    public DefaultValidationRules add(ValidationRule<Difference> rule) {
        rules.add(rule);
        return this;
    }

    @Override
    public DefaultValidationRules validate(Iterable<Difference> differences) {
        Iterator<Difference> iter = differences.iterator();
        errors = new ArrayList<>();
        while (iter.hasNext()) {
            Difference next = iter.next();
            List<ValidationRule<Difference>> applicable = rules.stream()
                    .filter(r -> r.isApplicable(next))
                    .collect(Collectors.toList());
            if (applicable.size() > 1) {
                applicable.remove(allTrowAssertion);
            }
            applicable.forEach(rule -> rule.validate(next).ifPresent(errors::add));
        }
        return this;
    }

    @Override
    public Collection<AssertionError> getErrors() {
        return errors;
    }

}
