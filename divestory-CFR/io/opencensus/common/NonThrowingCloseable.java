/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import java.io.Closeable;

@Deprecated
public interface NonThrowingCloseable
extends Closeable {
    @Override
    public void close();
}

