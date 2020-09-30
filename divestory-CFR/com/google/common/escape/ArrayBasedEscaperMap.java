/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.escape;

import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArrayBasedEscaperMap {
    private static final char[][] EMPTY_REPLACEMENT_ARRAY = new char[0][0];
    private final char[][] replacementArray;

    private ArrayBasedEscaperMap(char[][] arrc) {
        this.replacementArray = arrc;
    }

    public static ArrayBasedEscaperMap create(Map<Character, String> map) {
        return new ArrayBasedEscaperMap(ArrayBasedEscaperMap.createReplacementArray(map));
    }

    static char[][] createReplacementArray(Map<Character, String> map) {
        Preconditions.checkNotNull(map);
        if (map.isEmpty()) {
            return EMPTY_REPLACEMENT_ARRAY;
        }
        char[][] arrarrc = new char[java.util.Collections.max(map.keySet()).charValue() + '\u0001'][];
        Iterator<Character> iterator2 = map.keySet().iterator();
        while (iterator2.hasNext()) {
            char c = iterator2.next().charValue();
            arrarrc[c] = map.get(Character.valueOf(c)).toCharArray();
        }
        return arrarrc;
    }

    char[][] getReplacementArray() {
        return this.replacementArray;
    }
}

