package io.github.phantomstr.testing.tool.utils.collection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Lists {

    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... elements) {
        if (elements == null) {
            return null;
        }
        ArrayList<T> list = new ArrayList<>();
        java.util.Collections.addAll(list, elements);
        return list;
    }

    @SafeVarargs
    public static <T> List<T> list(T... elements) {
        return newArrayList(elements);
    }

    public static <T> ArrayList<T> newArrayList(Iterable<? extends T> elements) {
        if (elements == null) {
            return null;
        }
        return Streams.stream(elements).collect(toCollection(ArrayList::new));
    }

    public static <T> ArrayList<T> newArrayList(Iterator<? extends T> elements) {
        if (elements == null) {
            return null;
        }
        return Streams.stream(elements).collect(toCollection(ArrayList::new));
    }

}
