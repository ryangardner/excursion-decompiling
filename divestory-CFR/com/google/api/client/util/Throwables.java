/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

public final class Throwables {
    private Throwables() {
    }

    public static RuntimeException propagate(Throwable throwable) {
        return com.google.common.base.Throwables.propagate(throwable);
    }

    public static void propagateIfPossible(Throwable throwable) {
        if (throwable == null) return;
        com.google.common.base.Throwables.throwIfUnchecked(throwable);
    }

    public static <X extends Throwable> void propagateIfPossible(Throwable throwable, Class<X> class_) throws Throwable {
        com.google.common.base.Throwables.propagateIfPossible(throwable, class_);
    }
}

