/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;

public abstract class OptionalPendingResult<R extends Result>
extends PendingResult<R> {
    public abstract R get();

    public abstract boolean isDone();
}

