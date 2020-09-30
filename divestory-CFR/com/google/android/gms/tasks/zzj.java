/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzi;

final class zzj
implements Runnable {
    private final /* synthetic */ Task zza;
    private final /* synthetic */ zzi zzb;

    zzj(zzi zzi2, Task task) {
        this.zzb = zzi2;
        this.zza = task;
    }

    @Override
    public final void run() {
        Object object = zzi.zza(this.zzb);
        synchronized (object) {
            if (zzi.zzb(this.zzb) == null) return;
            zzi.zzb(this.zzb).onComplete(this.zza);
            return;
        }
    }
}

