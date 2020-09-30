/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzo;
import com.google.android.gms.tasks.zzr;
import com.google.android.gms.tasks.zzu;
import java.util.concurrent.Executor;

final class zzp<TResult, TContinuationResult>
implements OnCanceledListener,
OnFailureListener,
OnSuccessListener<TContinuationResult>,
zzr<TResult> {
    private final Executor zza;
    private final SuccessContinuation<TResult, TContinuationResult> zzb;
    private final zzu<TContinuationResult> zzc;

    public zzp(Executor executor, SuccessContinuation<TResult, TContinuationResult> successContinuation, zzu<TContinuationResult> zzu2) {
        this.zza = executor;
        this.zzb = successContinuation;
        this.zzc = zzu2;
    }

    static /* synthetic */ SuccessContinuation zza(zzp zzp2) {
        return zzp2.zzb;
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
        this.zza.execute(new zzo(this, task));
    }
}

