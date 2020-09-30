/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Preconditions {
    private Preconditions() {
    }

    private static String badElementIndex(int n, int n2, @NullableDecl String charSequence) {
        if (n < 0) {
            return Strings.lenientFormat("%s (%s) must not be negative", charSequence, n);
        }
        if (n2 >= 0) {
            return Strings.lenientFormat("%s (%s) must be less than size (%s)", charSequence, n, n2);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("negative size: ");
        ((StringBuilder)charSequence).append(n2);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static String badPositionIndex(int n, int n2, @NullableDecl String charSequence) {
        if (n < 0) {
            return Strings.lenientFormat("%s (%s) must not be negative", charSequence, n);
        }
        if (n2 >= 0) {
            return Strings.lenientFormat("%s (%s) must not be greater than size (%s)", charSequence, n, n2);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("negative size: ");
        ((StringBuilder)charSequence).append(n2);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static String badPositionIndexes(int n, int n2, int n3) {
        if (n < 0) return Preconditions.badPositionIndex(n, n3, "start index");
        if (n > n3) {
            return Preconditions.badPositionIndex(n, n3, "start index");
        }
        if (n2 < 0) return Preconditions.badPositionIndex(n2, n3, "end index");
        if (n2 <= n3) return Strings.lenientFormat("end index (%s) must not be less than start index (%s)", n2, n);
        return Preconditions.badPositionIndex(n2, n3, "end index");
    }

    public static void checkArgument(boolean bl) {
        if (!bl) throw new IllegalArgumentException();
    }

    public static void checkArgument(boolean bl, @NullableDecl Object object) {
        if (!bl) throw new IllegalArgumentException(String.valueOf(object));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, char c) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, Character.valueOf(c)));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, char c, char c2) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, Character.valueOf(c), Character.valueOf(c2)));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, char c, int n) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, Character.valueOf(c), n));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, char c, long l) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, Character.valueOf(c), l));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, char c, @NullableDecl Object object) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, Character.valueOf(c), object));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, int n) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, n));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, int n, char c) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, n, Character.valueOf(c)));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, int n, int n2) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, n, n2));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, int n, long l) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, n, l));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, int n, @NullableDecl Object object) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, n, object));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, long l) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, l));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, long l, char c) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, l, Character.valueOf(c)));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, long l, int n) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, l, n));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, long l, long l2) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, l, l2));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, long l, @NullableDecl Object object) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, l, object));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, @NullableDecl Object object) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, object));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, @NullableDecl Object object, char c) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, object, Character.valueOf(c)));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, @NullableDecl Object object, int n) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, object, n));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, @NullableDecl Object object, long l) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, object, l));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, object, object2));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2, @NullableDecl Object object3) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, object, object2, object3));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2, @NullableDecl Object object3, @NullableDecl Object object4) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, object, object2, object3, object4));
    }

    public static void checkArgument(boolean bl, @NullableDecl String string2, @NullableDecl Object ... arrobject) {
        if (!bl) throw new IllegalArgumentException(Strings.lenientFormat(string2, arrobject));
    }

    public static int checkElementIndex(int n, int n2) {
        return Preconditions.checkElementIndex(n, n2, "index");
    }

    public static int checkElementIndex(int n, int n2, @NullableDecl String string2) {
        if (n < 0) throw new IndexOutOfBoundsException(Preconditions.badElementIndex(n, n2, string2));
        if (n >= n2) throw new IndexOutOfBoundsException(Preconditions.badElementIndex(n, n2, string2));
        return n;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t) {
        if (t == null) throw null;
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl Object object) {
        if (t == null) throw new NullPointerException(String.valueOf(object));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, char c) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, Character.valueOf(c)));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, char c, char c2) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, Character.valueOf(c), Character.valueOf(c2)));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, char c, int n) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, Character.valueOf(c), n));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, char c, long l) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, Character.valueOf(c), l));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, char c, @NullableDecl Object object) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, Character.valueOf(c), object));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, int n) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, n));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, int n, char c) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, n, Character.valueOf(c)));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, int n, int n2) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, n, n2));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, int n, long l) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, n, l));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, int n, @NullableDecl Object object) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, n, object));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, long l) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, l));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, long l, char c) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, l, Character.valueOf(c)));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, long l, int n) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, l, n));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, long l, long l2) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, l, l2));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, long l, @NullableDecl Object object) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, l, object));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, @NullableDecl Object object) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, object));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, @NullableDecl Object object, char c) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, object, Character.valueOf(c)));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, @NullableDecl Object object, int n) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, object, n));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, @NullableDecl Object object, long l) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, object, l));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, object, object2));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2, @NullableDecl Object object3) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, object, object2, object3));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2, @NullableDecl Object object3, @NullableDecl Object object4) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, object, object2, object3, object4));
        return t;
    }

    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t, @NullableDecl String string2, @NullableDecl Object ... arrobject) {
        if (t == null) throw new NullPointerException(Strings.lenientFormat(string2, arrobject));
        return t;
    }

    public static int checkPositionIndex(int n, int n2) {
        return Preconditions.checkPositionIndex(n, n2, "index");
    }

    public static int checkPositionIndex(int n, int n2, @NullableDecl String string2) {
        if (n < 0) throw new IndexOutOfBoundsException(Preconditions.badPositionIndex(n, n2, string2));
        if (n > n2) throw new IndexOutOfBoundsException(Preconditions.badPositionIndex(n, n2, string2));
        return n;
    }

    public static void checkPositionIndexes(int n, int n2, int n3) {
        if (n < 0) throw new IndexOutOfBoundsException(Preconditions.badPositionIndexes(n, n2, n3));
        if (n2 < n) throw new IndexOutOfBoundsException(Preconditions.badPositionIndexes(n, n2, n3));
        if (n2 > n3) throw new IndexOutOfBoundsException(Preconditions.badPositionIndexes(n, n2, n3));
    }

    public static void checkState(boolean bl) {
        if (!bl) throw new IllegalStateException();
    }

    public static void checkState(boolean bl, @NullableDecl Object object) {
        if (!bl) throw new IllegalStateException(String.valueOf(object));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, char c) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, Character.valueOf(c)));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, char c, char c2) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, Character.valueOf(c), Character.valueOf(c2)));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, char c, int n) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, Character.valueOf(c), n));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, char c, long l) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, Character.valueOf(c), l));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, char c, @NullableDecl Object object) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, Character.valueOf(c), object));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, int n) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, n));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, int n, char c) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, n, Character.valueOf(c)));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, int n, int n2) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, n, n2));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, int n, long l) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, n, l));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, int n, @NullableDecl Object object) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, n, object));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, long l) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, l));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, long l, char c) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, l, Character.valueOf(c)));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, long l, int n) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, l, n));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, long l, long l2) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, l, l2));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, long l, @NullableDecl Object object) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, l, object));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, @NullableDecl Object object) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, object));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, @NullableDecl Object object, char c) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, object, Character.valueOf(c)));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, @NullableDecl Object object, int n) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, object, n));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, @NullableDecl Object object, long l) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, object, l));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, object, object2));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2, @NullableDecl Object object3) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, object, object2, object3));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2, @NullableDecl Object object3, @NullableDecl Object object4) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, object, object2, object3, object4));
    }

    public static void checkState(boolean bl, @NullableDecl String string2, @NullableDecl Object ... arrobject) {
        if (!bl) throw new IllegalStateException(Strings.lenientFormat(string2, arrobject));
    }
}

