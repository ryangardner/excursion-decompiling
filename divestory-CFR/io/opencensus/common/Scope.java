/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.NonThrowingCloseable;

public interface Scope
extends NonThrowingCloseable {
    @Override
    public void close();
}

