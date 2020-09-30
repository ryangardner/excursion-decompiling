/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zze;
import com.google.android.gms.tasks.zzr;
import com.google.android.gms.tasks.zzu;
import java.util.concurrent.Executor;

final class zzc<TResult, TContinuationResult>
implements zzr<TResult> {
    private final Executor zza;
    private final Continuation<TResult, TContinuationResult> zzb;
    private final zzu<TContinuationResult> zzc;

    public zzc(Executor executor, Continuation<TResult, TContinuationResult> continuation2, zzu<TContinuationResult> zzu2) {
        this.zza = executor;
        this.zzb = continuation2;
        this.zzc = zzu2;
    }

    static /* synthetic */ zzu zza(zzc zzc2) {
        return zzc2.zzc;
    }

    static /* synthetic */ Continuation zzb(zzc zzc2) {
        return zzc2.zzb;
    }

    @Override
    public final void zza() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void zza(Task<TResult> task) {
        this.zza.execute(new zze(this, task));
    }
}

