/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean bl) {
        com.google.common.base.Preconditions.checkArgument(bl);
    }

    public static void checkArgument(boolean bl, Object object) {
        com.google.common.base.Preconditions.checkArgument(bl, object);
    }

    public static void checkArgument(boolean bl, String string2, Object ... arrobject) {
        com.google.common.base.Preconditions.checkArgument(bl, string2, arrobject);
    }

    public static <T> T checkNotNull(T t) {
        return com.google.common.base.Preconditions.checkNotNull(t);
    }

    public static <T> T checkNotNull(T t, Object object) {
        return com.google.common.base.Preconditions.checkNotNull(t, object);
    }

    public static <T> T checkNotNull(T t, String string2, Object ... arrobject) {
        return com.google.common.base.Preconditions.checkNotNull(t, string2, arrobject);
    }

    public static void checkState(boolean bl) {
        com.google.common.base.Preconditions.checkState(bl);
    }

    public static void checkState(boolean bl, Object object) {
        com.google.common.base.Preconditions.checkState(bl, object);
    }

    public static void checkState(boolean bl, String string2, Object ... arrobject) {
        com.google.common.base.Preconditions.checkState(bl, string2, arrobject);
    }
}

