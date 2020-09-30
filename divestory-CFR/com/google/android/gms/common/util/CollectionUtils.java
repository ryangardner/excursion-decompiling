/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util;

import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        if (collection != null) return collection.isEmpty();
        return true;
    }

    @Deprecated
    public static <T> List<T> listOf() {
        return Collections.emptyList();
    }

    @Deprecated
    public static <T> List<T> listOf(T t) {
        return Collections.singletonList(t);
    }

    @Deprecated
    public static <T> List<T> listOf(T ... arrT) {
        int n = arrT.length;
        if (n == 0) return CollectionUtils.listOf();
        if (n == 1) return CollectionUtils.listOf(arrT[0]);
        return Collections.unmodifiableList(Arrays.asList(arrT));
    }

    public static <K, V> Map<K, V> mapOf(K k, V v, K k2, V v2, K k3, V v3) {
        Map<K, V> map = CollectionUtils.zzb(3, false);
        map.put(k, v);
        map.put(k2, v2);
        map.put(k3, v3);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> Map<K, V> mapOf(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        Map<K, V> map = CollectionUtils.zzb(6, false);
        map.put(k, v);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> Map<K, V> mapOfKeyValueArrays(K[] object, V[] arrV) {
        if (((K[])object).length != arrV.length) {
            int n = ((K[])object).length;
            int n2 = arrV.length;
            object = new StringBuilder(66);
            ((StringBuilder)object).append("Key and values array lengths not equal: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" != ");
            ((StringBuilder)object).append(n2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        int n = ((K[])object).length;
        if (n == 0) return Collections.emptyMap();
        int n3 = 0;
        if (n == 1) return Collections.singletonMap(object[0], arrV[0]);
        Map<Object, V> map = CollectionUtils.zzb(((K[])object).length, false);
        while (n3 < ((Object)object).length) {
            map.put(object[n3], arrV[n3]);
            ++n3;
        }
        return Collections.unmodifiableMap(map);
    }

    public static <T> Set<T> mutableSetOfWithSize(int n) {
        if (n != 0) return CollectionUtils.zza(n, true);
        return new ArraySet();
    }

    @Deprecated
    public static <T> Set<T> setOf(T t, T t2, T t3) {
        Set<T> set = CollectionUtils.zza(3, false);
        set.add(t);
        set.add(t2);
        set.add(t3);
        return Collections.unmodifiableSet(set);
    }

    @Deprecated
    public static <T> Set<T> setOf(T ... object) {
        int n = ((T[])object).length;
        if (n == 0) return Collections.emptySet();
        if (n == 1) return Collections.singleton(object[0]);
        if (n == 2) {
            T t = object[0];
            object = object[1];
            Set<T> set = CollectionUtils.zza(2, false);
            set.add(t);
            set.add(object);
            return Collections.unmodifiableSet(set);
        }
        if (n == 3) return CollectionUtils.setOf(object[0], object[1], object[2]);
        if (n != 4) {
            Set<T> set = CollectionUtils.zza(((T[])object).length, false);
            Collections.addAll(set, object);
            return Collections.unmodifiableSet(set);
        }
        T t = object[0];
        T t2 = object[1];
        T t3 = object[2];
        object = object[3];
        Set<T> set = CollectionUtils.zza(4, false);
        set.add(t);
        set.add(t2);
        set.add(t3);
        set.add(object);
        return Collections.unmodifiableSet(set);
    }

    private static <T> Set<T> zza(int n, boolean bl) {
        float f = bl ? 0.75f : 1.0f;
        int n2 = bl ? 128 : 256;
        if (n > n2) return new HashSet(n, f);
        return new ArraySet(n);
    }

    private static <K, V> Map<K, V> zzb(int n, boolean bl) {
        if (n > 256) return new HashMap(n, 1.0f);
        return new ArrayMap(n);
    }
}

