/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzm;

final class zzn
implements Runnable {
    private final /* synthetic */ Task zza;
    private final /* synthetic */ zzm zzb;

    zzn(zzm zzm2, Task task) {
        this.zzb = zzm2;
        this.zza = task;
    }

    @Override
    public final void run() {
        Object object = zzm.zza(this.zzb);
        synchronized (object) {
            if (zzm.zzb(this.zzb) == null) return;
            zzm.zzb(this.zzb).onSuccess(this.zza.getResult());
            return;
        }
    }
}

