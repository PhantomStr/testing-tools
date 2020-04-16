package com.phantomstr.testing.tool.utils.function;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionUtil {
    public static <T, R> List<R> toList(Collection<T> collection, Function<T, R> mapFunction) {
        return collection.stream().map(mapFunction).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T, U, R> List<R> toList(Collection<T> collection, Function<T, U> mapFunction, Function<U, R> subMapFunction) {
        return collection.stream().map(mapFunction).map(subMapFunction).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T, R> List<R> toList(T[] array, Function<T, R> mapFunction) {
        return Arrays.stream(array).map(mapFunction).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T, U, R> List<R> toList(T[] array, Function<T, U> mapFunction, Function<U, R> subMapFunction) {
        return Arrays.stream(array).map(mapFunction).map(subMapFunction).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T> long count(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).count();
    }

    public static <T, R> R mapIfNotNull(T nullable, Function<T, R> method) {
        return Optional.ofNullable(nullable).map(method).orElse(null);
    }

}
