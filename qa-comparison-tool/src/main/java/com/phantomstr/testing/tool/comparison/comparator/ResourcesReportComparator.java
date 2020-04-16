package com.phantomstr.testing.tool.comparison.comparator;

import com.phantomstr.testing.tool.comparison.report.ResourcesReportProvider;
import com.phantomstr.testing.tool.comparison.rule.DefaultValidationRules;


public class ResourcesReportComparator extends AbstractComparator<ResourcesReportComparator> {
    private ResourcesReportComparator() {
        super();
    }

    public ResourcesReportComparator(ResourcesReportProvider reportProvider, DefaultValidationRules validationRules) {
        super(new ResourcesReportComparator(), reportProvider, validationRules);
    }

    public static ResourcesReportComparator create() {
        return new ResourcesReportComparator(new ResourcesReportProvider(), new DefaultValidationRules()).myself;
    }

}
