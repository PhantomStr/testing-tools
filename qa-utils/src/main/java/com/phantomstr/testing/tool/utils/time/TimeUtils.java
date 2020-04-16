package com.phantomstr.testing.tool.utils.time;

import java.time.Duration;

public class TimeUtils {
    public static String humanReadableFormat(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

}
