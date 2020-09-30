/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzf;
import com.google.android.gms.tasks.zzr;
import com.google.android.gms.tasks.zzu;
import java.util.concurrent.Executor;

final class zzd<TResult, TContinuationResult>
implements OnCanceledListener,
OnFailureListener,
OnSuccessListener<TContinuationResult>,
zzr<TResult> {
    private final Executor zza;
    private final Continuation<TResult, Task<TContinuationResult>> zzb;
    private final zzu<TContinuationResult> zzc;

    public zzd(Executor executor, Continuation<TResult, Task<TContinuationResult>> continuation2, zzu<TContinuationResult> zzu2) {
        this.zza = executor;
        this.zzb = continuation2;
        this.zzc = zzu2;
    }

    static /* synthetic */ Continuation zza(zzd zzd2) {
        return zzd2.zzb;
    }

    static /* synthetic */ zzu zzb(zzd zzd2) {
        return zzd2.zzc;
    }

    @Override
    public final void onCanceled() {
        this.zzc.zza();
    }

    @Override
    public final void onFailure(Exception exception) {
        this.zzc.zza(exception);
    }

    @Override
    public final void onSuccess(TContinuationResult TContinuationResult) {
        this.zzc.zza(TContinuationResult);
    }

    @Override
    public final void zza() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void zza(Task<TResult> task) {
        this.zza.execute(new zzf(this, task));
    }
}

