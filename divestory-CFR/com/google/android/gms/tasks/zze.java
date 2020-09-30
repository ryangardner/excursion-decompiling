/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzc;

final class zze
implements Runnable {
    private final /* synthetic */ Task zza;
    private final /* synthetic */ zzc zzb;

    zze(zzc zzc2, Task task) {
        this.zzb = zzc2;
        this.zza = task;
    }

    @Override
    public final void run() {
        Object TContinuationResult;
        if (this.zza.isCanceled()) {
            zzc.zza(this.zzb).zza();
            return;
        }
        try {
            TContinuationResult = zzc.zzb(this.zzb).then(this.zza);
        }
        catch (Exception exception) {
            zzc.zza(this.zzb).zza(exception);
            return;
        }
        catch (RuntimeExecutionException runtimeExecutionException) {
            if (runtimeExecutionException.getCause() instanceof Exception) {
                zzc.zza(this.zzb).zza((Exception)runtimeExecutionException.getCause());
                return;
            }
            zzc.zza(this.zzb).zza(runtimeExecutionException);
            return;
        }
        zzc.zza(this.zzb).zza(TContinuationResult);
        return;
    }
}

