/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface Predicate<T> {
    public boolean apply(@NullableDecl T var1);

    public boolean equals(@NullableDecl Object var1);
}

