/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzg;
import com.google.android.gms.tasks.zzr;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

final class zzh<TResult>
implements zzr<TResult> {
    private final Executor zza;
    private final Object zzb = new Object();
    @Nullable
    private OnCanceledListener zzc;

    public zzh(Executor executor, OnCanceledListener onCanceledListener) {
        this.zza = executor;
        this.zzc = onCanceledListener;
    }

    static /* synthetic */ Object zza(zzh zzh2) {
        return zzh2.zzb;
    }

    static /* synthetic */ OnCanceledListener zzb(zzh zzh2) {
        return zzh2.zzc;
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
    public final void zza(Task<TResult> object) {
        if (!((Task)object).isCanceled()) return;
        object = this.zzb;
        synchronized (object) {
            if (this.zzc == null) {
                return;
            }
        }
        this.zza.execute(new zzg(this));
    }
}

