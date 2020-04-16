package com.phantomstr.testing.tool.utils.collection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Maps {

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> newHashMap(Object... keyValues) {
        if (keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("Expected even number of parameters: newHashMap(Object...keyValues)");
        }
        HashMap<K, V> map = new HashMap<>();
        Iterator<Object> iterator = IterableUtils.iterator(keyValues);
        while (iterator.hasNext()) {
            Object key = iterator.next();
            if (key != null) {
                Object val = iterator.next();
                map.put((K) key, (V) val);
            }
        }
        return map;
    }

}
