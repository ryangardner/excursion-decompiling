/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Platform {
    private Platform() {
    }

    static boolean isInstanceOfThrowableClass(@NullableDecl Throwable throwable, Class<? extends Throwable> class_) {
        return class_.isInstance(throwable);
    }
}

