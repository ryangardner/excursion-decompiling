/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;

public interface SuccessContinuation<TResult, TContinuationResult> {
    public Task<TContinuationResult> then(TResult var1) throws Exception;
}

