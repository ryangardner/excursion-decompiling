/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.util;

public final class LangUtils {
    public static final int HASH_OFFSET = 37;
    public static final int HASH_SEED = 17;

    private LangUtils() {
    }

    public static boolean equals(Object object, Object object2) {
        if (object != null) {
            return object.equals(object2);
        }
        if (object2 != null) return false;
        return true;
    }

    public static boolean equals(Object[] arrobject, Object[] arrobject2) {
        if (arrobject == null) {
            if (arrobject2 != null) return false;
            return true;
        }
        if (arrobject2 == null) return false;
        if (arrobject.length != arrobject2.length) return false;
        int n = 0;
        while (n < arrobject.length) {
            if (!LangUtils.equals(arrobject[n], arrobject2[n])) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static int hashCode(int n, int n2) {
        return n * 37 + n2;
    }

    public static int hashCode(int n, Object object) {
        int n2;
        if (object != null) {
            n2 = object.hashCode();
            return LangUtils.hashCode(n, n2);
        }
        n2 = 0;
        return LangUtils.hashCode(n, n2);
    }

    public static int hashCode(int n, boolean bl) {
        return LangUtils.hashCode(n, (int)bl);
    }
}

