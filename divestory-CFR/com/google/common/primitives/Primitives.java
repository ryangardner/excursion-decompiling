/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class Primitives {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap(16);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(16);
        Primitives.add(linkedHashMap, linkedHashMap2, Boolean.TYPE, Boolean.class);
        Primitives.add(linkedHashMap, linkedHashMap2, Byte.TYPE, Byte.class);
        Primitives.add(linkedHashMap, linkedHashMap2, Character.TYPE, Character.class);
        Primitives.add(linkedHashMap, linkedHashMap2, Double.TYPE, Double.class);
        Primitives.add(linkedHashMap, linkedHashMap2, Float.TYPE, Float.class);
        Primitives.add(linkedHashMap, linkedHashMap2, Integer.TYPE, Integer.class);
        Primitives.add(linkedHashMap, linkedHashMap2, Long.TYPE, Long.class);
        Primitives.add(linkedHashMap, linkedHashMap2, Short.TYPE, Short.class);
        Primitives.add(linkedHashMap, linkedHashMap2, Void.TYPE, Void.class);
        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(linkedHashMap);
        WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(linkedHashMap2);
    }

    private Primitives() {
    }

    private static void add(Map<Class<?>, Class<?>> map, Map<Class<?>, Class<?>> map2, Class<?> class_, Class<?> class_2) {
        map.put(class_, class_2);
        map2.put(class_2, class_);
    }

    public static Set<Class<?>> allPrimitiveTypes() {
        return PRIMITIVE_TO_WRAPPER_TYPE.keySet();
    }

    public static Set<Class<?>> allWrapperTypes() {
        return WRAPPER_TO_PRIMITIVE_TYPE.keySet();
    }

    public static boolean isWrapperType(Class<?> class_) {
        return WRAPPER_TO_PRIMITIVE_TYPE.containsKey(Preconditions.checkNotNull(class_));
    }

    public static <T> Class<T> unwrap(Class<T> class_) {
        Preconditions.checkNotNull(class_);
        Class<?> class_2 = WRAPPER_TO_PRIMITIVE_TYPE.get(class_);
        if (class_2 != null) return class_2;
        return class_;
    }

    public static <T> Class<T> wrap(Class<T> class_) {
        Preconditions.checkNotNull(class_);
        Class<?> class_2 = PRIMITIVE_TO_WRAPPER_TYPE.get(class_);
        if (class_2 != null) return class_2;
        return class_;
    }
}

