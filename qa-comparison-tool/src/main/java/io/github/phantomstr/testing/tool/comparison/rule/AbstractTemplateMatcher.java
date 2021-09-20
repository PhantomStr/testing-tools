package io.github.phantomstr.testing.tool.comparison.rule;

import lombok.AllArgsConstructor;
import org.xmlunit.diff.Difference;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@AllArgsConstructor
public abstract class AbstractTemplateMatcher implements ValidationRule<Difference> {
    protected String pathTemplate;

    @Override
    public Optional<? extends AssertionError> validate(Difference difference) {
        try {
            return ofNullable(checkRule(difference));
        } catch (AssertionError e) {
            return of(e);
        }
    }

    @Override
    public boolean isApplicable(Difference difference) {
        String xPath = (difference.getComparison().getControlDetails().getXPath() != null)
                ? difference.getComparison().getControlDetails().getXPath()
                : difference.getComparison().getTestDetails().getXPath();
        return xPath.matches(pathTemplate);
    }

    protected abstract AssertionError checkRule(Difference difference) throws AssertionError;

}
