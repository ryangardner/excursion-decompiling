/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.internal;

import io.opencensus.common.Scope;

public final class NoopScope
implements Scope {
    private static final Scope INSTANCE = new NoopScope();

    private NoopScope() {
    }

    public static Scope getInstance() {
        return INSTANCE;
    }

    @Override
    public void close() {
    }
}

