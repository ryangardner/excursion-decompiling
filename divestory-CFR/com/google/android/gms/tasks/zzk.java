/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzl;

final class zzk
implements Runnable {
    private final /* synthetic */ Task zza;
    private final /* synthetic */ zzl zzb;

    zzk(zzl zzl2, Task task) {
        this.zzb = zzl2;
        this.zza = task;
    }

    @Override
    public final void run() {
        Object object = zzl.zza(this.zzb);
        synchronized (object) {
            if (zzl.zzb(this.zzb) == null) return;
            zzl.zzb(this.zzb).onFailure(Preconditions.checkNotNull(this.zza.getException()));
            return;
        }
    }
}

