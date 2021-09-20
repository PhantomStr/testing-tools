package io.github.phantomstr.testing.tool.comparison.rule;

import lombok.AllArgsConstructor;
import org.xmlunit.diff.Difference;

import java.util.Optional;

@AllArgsConstructor
public class AllTrowAssertion implements ValidationRule<Difference> {
    @Override
    public Optional<? extends AssertionError> validate(Difference difference) {
        return Optional.of(new AssertionError(difference.toString()));
    }

    @Override
    public boolean isApplicable(Difference difference) {
        return true;
    }

}
