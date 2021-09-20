package io.github.phantomstr.testing.tool.comparison.report;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourcesReportProvider implements ReportProvider {
    @Override
    public Report getReport(UUID runId, String env) {
        return Report.builder()
                .id(runId)
                .content(readFileContent(runId, env))
                .build();
    }

    @SneakyThrows
    private String readFileContent(UUID id, String env) {
        String resourcePath = String.format("reports/%s/%s.xml", env, id);
        URL resource = ResourcesReportProvider.class.getClassLoader().getResource(resourcePath);
        assertThat(resource).isNotNull();
        return FileUtils.readFileToString(new File(resource.toURI()));
    }

}
