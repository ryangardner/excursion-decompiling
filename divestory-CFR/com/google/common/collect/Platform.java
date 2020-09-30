/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Strings;
import com.google.common.collect.CompactHashMap;
import com.google.common.collect.CompactHashSet;
import com.google.common.collect.CompactLinkedHashMap;
import com.google.common.collect.CompactLinkedHashSet;
import com.google.common.collect.MapMaker;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

final class Platform {
    private static final String GWT_RPC_PROPERTY_NAME = "guava.gwt.emergency_reenable_rpc";

    private Platform() {
    }

    static void checkGwtRpcEnabled() {
        if (!Boolean.parseBoolean(System.getProperty(GWT_RPC_PROPERTY_NAME, "true"))) throw new UnsupportedOperationException(Strings.lenientFormat("We are removing GWT-RPC support for Guava types. You can temporarily reenable support by setting the system property %s to true. For more about system properties, see %s. For more about Guava's GWT-RPC support, see %s.", GWT_RPC_PROPERTY_NAME, "https://stackoverflow.com/q/5189914/28465", "https://groups.google.com/d/msg/guava-announce/zHZTFg7YF3o/rQNnwdHeEwAJ"));
    }

    static <T> T[] copy(Object[] arrobject, int n, int n2, T[] arrT) {
        return Arrays.copyOfRange(arrobject, n, n2, arrT.getClass());
    }

    static <T> T[] newArray(T[] arrT, int n) {
        return (Object[])Array.newInstance(arrT.getClass().getComponentType(), n);
    }

    static <K, V> Map<K, V> newHashMapWithExpectedSize(int n) {
        return CompactHashMap.createWithExpectedSize(n);
    }

    static <E> Set<E> newHashSetWithExpectedSize(int n) {
        return CompactHashSet.createWithExpectedSize(n);
    }

    static <K, V> Map<K, V> newLinkedHashMapWithExpectedSize(int n) {
        return CompactLinkedHashMap.createWithExpectedSize(n);
    }

    static <E> Set<E> newLinkedHashSetWithExpectedSize(int n) {
        return CompactLinkedHashSet.createWithExpectedSize(n);
    }

    static <E> Set<E> preservesInsertionOrderOnAddsSet() {
        return CompactHashSet.create();
    }

    static <K, V> Map<K, V> preservesInsertionOrderOnPutsMap() {
        return CompactHashMap.create();
    }

    static int reduceExponentIfGwt(int n) {
        return n;
    }

    static int reduceIterationsIfGwt(int n) {
        return n;
    }

    static MapMaker tryWeakKeys(MapMaker mapMaker) {
        return mapMaker.weakKeys();
    }
}

