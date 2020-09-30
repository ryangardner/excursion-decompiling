/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzs;
import com.google.android.gms.tasks.zzu;

public class TaskCompletionSource<TResult> {
    private final zzu<TResult> zza = new zzu();

    public TaskCompletionSource() {
    }

    public TaskCompletionSource(CancellationToken cancellationToken) {
        cancellationToken.onCanceledRequested(new zzs(this));
    }

    static /* synthetic */ zzu zza(TaskCompletionSource taskCompletionSource) {
        return taskCompletionSource.zza;
    }

    public Task<TResult> getTask() {
        return this.zza;
    }

    public void setException(Exception exception) {
        this.zza.zza(exception);
    }

    public void setResult(TResult TResult) {
        this.zza.zza(TResult);
    }

    public boolean trySetException(Exception exception) {
        return this.zza.zzb(exception);
    }

    public boolean trySetResult(TResult TResult) {
        return this.zza.zzb(TResult);
    }
}

