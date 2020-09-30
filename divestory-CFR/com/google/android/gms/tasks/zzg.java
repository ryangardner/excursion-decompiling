/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.zzh;

final class zzg
implements Runnable {
    private final /* synthetic */ zzh zza;

    zzg(zzh zzh2) {
        this.zza = zzh2;
    }

    @Override
    public final void run() {
        Object object = zzh.zza(this.zza);
        synchronized (object) {
            if (zzh.zzb(this.zza) == null) return;
            zzh.zzb(this.zza).onCanceled();
            return;
        }
    }
}

