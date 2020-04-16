package com.phantomstr.testing.tool.utils.lang;

import com.ibm.icu.text.Transliterator;

public class TransliterateUtils {
    public static final String LATIN_TO_RUSSIAN = "Latin-Russian/BGN";
    public static final String RUSSIAN_TO_LATIN = "Russian-Latin/BGN";

    public static String latinToRussian(String str) {
        return Transliterator.getInstance(LATIN_TO_RUSSIAN).transliterate(str);
    }

    public static String russianToLatin(String str) {
        return Transliterator.getInstance(RUSSIAN_TO_LATIN).transliterate(str);
    }
}
