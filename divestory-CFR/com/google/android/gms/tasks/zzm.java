/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzn;
import com.google.android.gms.tasks.zzr;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

final class zzm<TResult>
implements zzr<TResult> {
    private final Executor zza;
    private final Object zzb = new Object();
    @Nullable
    private OnSuccessListener<? super TResult> zzc;

    public zzm(Executor executor, OnSuccessListener<? super TResult> onSuccessListener) {
        this.zza = executor;
        this.zzc = onSuccessListener;
    }

    static /* synthetic */ Object zza(zzm zzm2) {
        return zzm2.zzb;
    }

    static /* synthetic */ OnSuccessListener zzb(zzm zzm2) {
        return zzm2.zzc;
    }

    @Override
    public final void zza() {
        Object object = this.zzb;
        synchronized (object) {
            this.zzc = null;
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public final void zza(Task<TResult> task) {
        if (!task.isSuccessful()) return;
        Object object = this.zzb;
        synchronized (object) {
            if (this.zzc == null) {
                return;
            }
        }
        this.zza.execute(new zzn(this, task));
    }
}

