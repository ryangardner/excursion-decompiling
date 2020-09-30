/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzj;
import com.google.android.gms.tasks.zzr;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

final class zzi<TResult>
implements zzr<TResult> {
    private final Executor zza;
    private final Object zzb = new Object();
    @Nullable
    private OnCompleteListener<TResult> zzc;

    public zzi(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        this.zza = executor;
        this.zzc = onCompleteListener;
    }

    static /* synthetic */ Object zza(zzi zzi2) {
        return zzi2.zzb;
    }

    static /* synthetic */ OnCompleteListener zzb(zzi zzi2) {
        return zzi2.zzc;
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
        Object object = this.zzb;
        synchronized (object) {
            if (this.zzc == null) {
                return;
            }
        }
        this.zza.execute(new zzj(this, task));
    }
}

