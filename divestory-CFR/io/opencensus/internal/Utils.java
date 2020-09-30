/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public final class Utils {
    private Utils() {
    }

    public static void checkArgument(boolean bl, @Nullable Object object) {
        if (!bl) throw new IllegalArgumentException(String.valueOf(object));
    }

    public static void checkArgument(boolean bl, String string2, @Nullable Object ... arrobject) {
        if (!bl) throw new IllegalArgumentException(Utils.format(string2, arrobject));
    }

    public static void checkIndex(int n, int n2) {
        if (n2 < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Negative size: ");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n >= 0 && n < n2) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index out of bounds: size=");
        stringBuilder.append(n2);
        stringBuilder.append(", index=");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public static <T> void checkListElementNotNull(List<T> object, @Nullable Object object2) {
        object = object.iterator();
        while (object.hasNext()) {
            if (object.next() == null) throw new NullPointerException(String.valueOf(object2));
        }
    }

    public static <K, V> void checkMapElementNotNull(Map<K, V> entry, @Nullable Object object) {
        Iterator<Map.Entry<K, V>> iterator2 = entry.entrySet().iterator();
        while (iterator2.hasNext()) {
            entry = iterator2.next();
            if (entry.getKey() == null) throw new NullPointerException(String.valueOf(object));
            if (entry.getValue() == null) throw new NullPointerException(String.valueOf(object));
        }
    }

    public static <T> T checkNotNull(T t, @Nullable Object object) {
        if (t == null) throw new NullPointerException(String.valueOf(object));
        return t;
    }

    public static void checkState(boolean bl, @Nullable Object object) {
        if (!bl) throw new IllegalStateException(String.valueOf(object));
    }

    public static boolean equalsObjects(@Nullable Object object, @Nullable Object object2) {
        if (object != null) {
            return object.equals(object2);
        }
        if (object2 != null) return false;
        return true;
    }

    private static String format(String string2, @Nullable Object ... arrobject) {
        int n;
        int n2;
        if (arrobject == null) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder(string2.length() + arrobject.length * 16);
        int n3 = 0;
        for (n2 = 0; n2 < arrobject.length && (n = string2.indexOf("%s", n3)) != -1; ++n2) {
            stringBuilder.append(string2, n3, n);
            stringBuilder.append(arrobject[n2]);
            n3 = n + 2;
        }
        stringBuilder.append(string2, n3, string2.length());
        if (n2 >= arrobject.length) return stringBuilder.toString();
        stringBuilder.append(" [");
        n3 = n2 + 1;
        stringBuilder.append(arrobject[n2]);
        n2 = n3;
        do {
            if (n2 >= arrobject.length) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            stringBuilder.append(arrobject[n2]);
            ++n2;
        } while (true);
    }
}

