package io.github.phantomstr.testing.tool.comparison.comparator;

import io.github.phantomstr.testing.tool.comparison.error.handler.RuleErrorHandler;
import io.github.phantomstr.testing.tool.comparison.report.ReportProvider;
import io.github.phantomstr.testing.tool.comparison.rule.ValidationRule;
import io.github.phantomstr.testing.tool.comparison.rule.ValidationRules;
import io.github.phantomstr.testing.tool.comparison.utils.SelfContainer;
import lombok.Getter;
import lombok.NonNull;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

import java.util.Collection;
import java.util.UUID;

@Getter
public abstract class AbstractComparator<SELF extends AbstractComparator<SELF>>
        extends SelfContainer<SELF>
        implements ValidationRules<SELF>, Comparator<SELF> {
    private ReportProvider reportProvider;
    private ValidationRules validationRules;
    private Diff diff;

    AbstractComparator() {
        super(null);
    }

    AbstractComparator(@NonNull SELF myself,
                       @NonNull ReportProvider reportProvider,
                       @NonNull ValidationRules validationRules) {
        super(myself);
        myself.setReportProvider(reportProvider);
        myself.setValidationRules(validationRules);
    }

    public SELF setReportProvider(ReportProvider reportProvider) {
        this.reportProvider = reportProvider;
        return myself;
    }

    public SELF setValidationRules(ValidationRules validationRules) {
        this.validationRules = validationRules;
        return myself;
    }

    public SELF setDiff(Diff diff) {
        this.diff = diff;
        return myself;
    }

    @Override
    public SELF add(ValidationRule<Difference> rule) {
        myself.getValidationRules().add(rule);
        return myself;
    }

    @Override
    public SELF validate(Iterable<Difference> differences) {
        myself.getValidationRules().validate(differences);
        return myself;
    }

    @Override
    public Collection<AssertionError> getErrors() {
        //noinspection unchecked
        return myself.getValidationRules().getErrors();
    }

    @Override
    public SELF compare(String sourceEnv, String targetEnv, UUID runId) {
        String controlXml = myself.getReportProvider().getReport(runId, sourceEnv).getContent();
        String testXml = myself.getReportProvider().getReport(runId, targetEnv).getContent();
        myself.setDiff(DiffBuilder.compare(controlXml).withTest(testXml).build());
        return myself;
    }

    @Override
    public SELF validate() {
        if (myself.getDiff() != null) {
            myself.getValidationRules().validate(diff.getDifferences());
        }
        return myself;
    }

    @Override
    public <R> R handle(RuleErrorHandler<R> handler) {
        //noinspection unchecked
        return (R) handler.handle(myself.getValidationRules().getErrors());
    }

}
