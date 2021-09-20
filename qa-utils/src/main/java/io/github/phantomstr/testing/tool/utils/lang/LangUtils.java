package io.github.phantomstr.testing.tool.utils.lang;

import java.util.stream.Stream;

public class LangUtils {
    public static boolean startWithCyrillicLetter(String string) {
        char c = string.charAt(0);
        return (c >= 'А' && c <= 'Я') || (c >= 'а' && c <= 'я');
    }

    public static boolean startWithCyrillicLetterAtLeastOne(String... string) {
        return Stream.of(string).anyMatch(LangUtils::startWithCyrillicLetter);
    }

    public static boolean startWithLatinLetter(String string) {
        char c = string.charAt(0);
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    public static boolean startWithLatinLetterAtLeastOne(String... string) {
        return Stream.of(string).anyMatch(LangUtils::startWithLatinLetter);
    }
}
