package io.github.phantomstr.testing.tool.comparison.report;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Report {
    UUID id;
    String content;

}
