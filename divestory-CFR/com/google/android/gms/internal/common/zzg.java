/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.common;

import com.google.android.gms.internal.common.zzf;
import com.google.android.gms.internal.common.zzh;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

final class zzg
implements zzf {
    private zzg() {
    }

    /* synthetic */ zzg(zzh zzh2) {
        this();
    }

    @Override
    public final ScheduledExecutorService zza(int n, int n2) {
        return Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(1));
    }
}

