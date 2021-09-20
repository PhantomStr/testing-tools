package io.github.phantomstr.testing.tool.utils.comparator;

import org.apache.commons.math3.util.Precision;

import java.util.Comparator;

import static java.util.Comparator.comparingDouble;

public class RoundDoubleComparator {
    public static Comparator<Double> roundDoubleComparator(int scale) {
        return comparingDouble(aDouble -> aDouble == null ? Double.NaN : Precision.round(aDouble, scale));
    }

    public static Comparator<Double> wholePartComparator() {
        return comparingDouble(aDouble -> aDouble == null ? Double.NaN : aDouble.intValue());
    }

    public static Comparator<Double> maxDifComparator(double epc) {
        return (o1, o2) -> Precision.equalsIncludingNaN(o1, o2, epc) ? 0 : o1 > o2 ? 1 : -1;
    }

}
