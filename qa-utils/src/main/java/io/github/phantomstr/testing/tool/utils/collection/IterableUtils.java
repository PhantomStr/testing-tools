package io.github.phantomstr.testing.tool.utils.collection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.phantomstr.testing.tool.utils.collection.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IterableUtils {

    public static boolean isNullOrEmpty(Iterable<?> iterable) {
        if (iterable == null) return true;
        if (iterable instanceof Collection && ((Collection<?>) iterable).isEmpty()) return true;
        return !iterable.iterator().hasNext();
    }

    public static int sizeOf(Iterable<?> iterable) {
        requireNonNull(iterable, "Iterable must not be null");
        if (iterable instanceof Collection) return ((Collection<?>) iterable).size();
        return Math.toIntExact(Streams.stream(iterable).count());
    }

    public static <T> List<T> nonNullElementsIn(Iterable<? extends T> i) {
        if (isNullOrEmpty(i)) return emptyList();
        return Streams.stream(i).filter(Objects::nonNull).collect(toList());
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Iterable<? extends T> iterable) {
        if (iterable == null) return null;
        return (T[]) newArrayList(iterable).toArray();
    }

    public static <T> T[] toArray(Iterable<? extends T> iterable, Class<T> type) {
        if (iterable == null) return null;
        Collection<? extends T> collection = toCollection(iterable);
        T[] array = newArray(type, collection.size());
        return collection.toArray(array);
    }

    public static <T> Collection<T> toCollection(Iterable<T> iterable) {
        return iterable instanceof Collection ? (Collection<T>) iterable : newArrayList(iterable);
    }

    @SafeVarargs
    public static <T> Iterable<T> iterable(T... elements) {
        if (elements == null) return null;
        ArrayList<T> list = newArrayList();
        java.util.Collections.addAll(list, elements);
        return list;
    }

    @SafeVarargs
    public static <T> Iterator<T> iterator(T... elements) {
        if (elements == null) return null;
        return iterable(elements).iterator();
    }

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return (iterable instanceof Collection)
                ? ((Collection<T>) iterable).stream()
                : StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] newArray(Class<T> type, int length) {
        return (T[]) Array.newInstance(type, length);
    }

}
