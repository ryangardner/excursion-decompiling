/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.google.android.gms.common.util.concurrent;

import android.os.Process;

final class zza
implements Runnable {
    private final Runnable zza;
    private final int zzb;

    public zza(Runnable runnable2, int n) {
        this.zza = runnable2;
        this.zzb = 0;
    }

    @Override
    public final void run() {
        Process.setThreadPriority((int)this.zzb);
        this.zza.run();
    }
}

