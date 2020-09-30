/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzk;
import com.google.android.gms.tasks.zzr;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

final class zzl<TResult>
implements zzr<TResult> {
    private final Executor zza;
    private final Object zzb = new Object();
    @Nullable
    private OnFailureListener zzc;

    public zzl(Executor executor, OnFailureListener onFailureListener) {
        this.zza = executor;
        this.zzc = onFailureListener;
    }

    static /* synthetic */ Object zza(zzl zzl2) {
        return zzl2.zzb;
    }

    static /* synthetic */ OnFailureListener zzb(zzl zzl2) {
        return zzl2.zzc;
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
        if (task.isSuccessful()) return;
        if (task.isCanceled()) return;
        Object object = this.zzb;
        synchronized (object) {
            if (this.zzc == null) {
                return;
            }
        }
        this.zza.execute(new zzk(this, task));
    }
}

