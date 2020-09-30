/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Converter;
import com.google.common.base.Optional;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Enums {
    private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> enumConstantCache = new WeakHashMap();

    private Enums() {
    }

    static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(Class<T> class_) {
        Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> map = enumConstantCache;
        synchronized (map) {
            Map<String, WeakReference<Enum<?>>> map2;
            Map<String, WeakReference<Enum<?>>> map3 = map2 = enumConstantCache.get(class_);
            if (map2 != null) return map3;
            return Enums.populateCache(class_);
        }
    }

    public static Field getField(Enum<?> object) {
        Class<?> class_ = ((Enum)object).getDeclaringClass();
        try {
            return class_.getDeclaredField(((Enum)object).name());
        }
        catch (NoSuchFieldException noSuchFieldException) {
            throw new AssertionError(noSuchFieldException);
        }
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> class_, String string2) {
        Preconditions.checkNotNull(class_);
        Preconditions.checkNotNull(string2);
        return Platform.getEnumIfPresent(class_, string2);
    }

    private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> populateCache(Class<T> class_) {
        HashMap hashMap = new HashMap();
        Iterator iterator2 = EnumSet.allOf(class_).iterator();
        do {
            if (!iterator2.hasNext()) {
                enumConstantCache.put(class_, hashMap);
                return hashMap;
            }
            Enum enum_ = (Enum)iterator2.next();
            hashMap.put(enum_.name(), new WeakReference<Enum>(enum_));
        } while (true);
    }

    public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> class_) {
        return new StringConverter<T>(class_);
    }

    private static final class StringConverter<T extends Enum<T>>
    extends Converter<String, T>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final Class<T> enumClass;

        StringConverter(Class<T> class_) {
            this.enumClass = Preconditions.checkNotNull(class_);
        }

        @Override
        protected String doBackward(T t) {
            return ((Enum)t).name();
        }

        @Override
        protected T doForward(String string2) {
            return Enum.valueOf(this.enumClass, string2);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof StringConverter)) return false;
            object = (StringConverter)object;
            return this.enumClass.equals(((StringConverter)object).enumClass);
        }

        public int hashCode() {
            return this.enumClass.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Enums.stringConverter(");
            stringBuilder.append(this.enumClass.getName());
            stringBuilder.append(".class)");
            return stringBuilder.toString();
        }
    }

}

