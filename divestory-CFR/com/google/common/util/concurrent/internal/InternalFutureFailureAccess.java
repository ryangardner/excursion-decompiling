/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent.internal;

public abstract class InternalFutureFailureAccess {
    protected InternalFutureFailureAccess() {
    }

    protected abstract Throwable tryInternalFastPathGetFailure();
}

