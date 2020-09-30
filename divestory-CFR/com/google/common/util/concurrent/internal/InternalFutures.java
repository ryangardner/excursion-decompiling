/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent.internal;

import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;

public final class InternalFutures {
    private InternalFutures() {
    }

    public static Throwable tryInternalFastPathGetFailure(InternalFutureFailureAccess internalFutureFailureAccess) {
        return internalFutureFailureAccess.tryInternalFastPathGetFailure();
    }
}

