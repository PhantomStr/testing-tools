package io.github.phantomstr.testing.tool.comparison.report;

import java.util.UUID;

public interface ReportProvider {
    Report getReport(UUID runId, String env);

}
