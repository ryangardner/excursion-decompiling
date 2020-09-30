/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zaci;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class TaskUtil {
    public static void setResultOrApiException(Status status, TaskCompletionSource<Void> taskCompletionSource) {
        TaskUtil.setResultOrApiException(status, null, taskCompletionSource);
    }

    public static <TResult> void setResultOrApiException(Status status, TResult TResult, TaskCompletionSource<TResult> taskCompletionSource) {
        if (status.isSuccess()) {
            taskCompletionSource.setResult(TResult);
            return;
        }
        taskCompletionSource.setException(new ApiException(status));
    }

    @Deprecated
    public static Task<Void> toVoidTaskThatFailsOnFalse(Task<Boolean> task) {
        return task.continueWith(new zaci());
    }

    public static <ResultT> boolean trySetResultOrApiException(Status status, ResultT ResultT, TaskCompletionSource<ResultT> taskCompletionSource) {
        if (!status.isSuccess()) return taskCompletionSource.trySetException(new ApiException(status));
        return taskCompletionSource.trySetResult(ResultT);
    }
}

