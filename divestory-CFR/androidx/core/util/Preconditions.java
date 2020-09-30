/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.core.util;

import java.util.Locale;

public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean bl) {
        if (!bl) throw new IllegalArgumentException();
    }

    public static void checkArgument(boolean bl, Object object) {
        if (!bl) throw new IllegalArgumentException(String.valueOf(object));
    }

    public static int checkArgumentInRange(int n, int n2, int n3, String string2) {
        if (n < n2) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", string2, n2, n3));
        }
        if (n > n3) throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", string2, n2, n3));
        return n;
    }

    public static int checkArgumentNonnegative(int n) {
        if (n < 0) throw new IllegalArgumentException();
        return n;
    }

    public static int checkArgumentNonnegative(int n, String string2) {
        if (n < 0) throw new IllegalArgumentException(string2);
        return n;
    }

    public static <T> T checkNotNull(T t) {
        if (t == null) throw null;
        return t;
    }

    public static <T> T checkNotNull(T t, Object object) {
        if (t == null) throw new NullPointerException(String.valueOf(object));
        return t;
    }

    public static void checkState(boolean bl) {
        Preconditions.checkState(bl, null);
    }

    public static void checkState(boolean bl, String string2) {
        if (!bl) throw new IllegalStateException(string2);
    }
}

