package io.github.phantomstr.testing.tool.comparison.rule;

import io.github.phantomstr.testing.tool.comparison.comparator.Comparator;
import io.github.phantomstr.testing.tool.comparison.comparator.ResourcesReportComparator;
import io.github.phantomstr.testing.tool.comparison.error.handler.CollectAndThrowExceptions;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xmlunit.diff.Difference;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Slf4j
public class ComparisonExampleTest {
    private Comparator comparator;

    @BeforeMethod
    public void setUp() {
        comparator = ResourcesReportComparator.create().add(new ProjectUpdatedRule());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void shouldCollectAndThrowExceptions() {
        String sourceEnv = "env-1";
        String targetEnv = "env-2";
        UUID runId = UUID.fromString("7761f76b-67ad-46f4-8826-c68fe991d1ae");

        comparator.compare(sourceEnv, targetEnv, runId)
                .validate()
                .handle(new CollectAndThrowExceptions());
    }

    private static class ProjectUpdatedRule extends AbstractTemplateMatcher {
        public ProjectUpdatedRule() {
            super("^/prodect\\[\\d*]/updated\\[\\d*]/text\\(\\)\\[\\d*]$");
        }

        @Override
        protected AssertionError checkRule(Difference difference) {
            return defaultIfNull(checkFormat(difference, ISO_OFFSET_DATE_TIME),
                    new AssertionError(format("<project>/<updated> expected %s but was %s",
                            difference.getComparison().getControlDetails().getValue(),
                            difference.getComparison().getTestDetails().getValue())));
        }

        private AssertionError checkFormat(Difference difference, DateTimeFormatter format) {
            try {
                OffsetDateTime.parse(difference.getComparison().getTestDetails().getValue().toString(), format);
            } catch (DateTimeParseException e) {
                return new AssertionError(e);
            }
            return null;
        }

    }

}
