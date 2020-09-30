/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.zzw;
import com.google.android.gms.tasks.zzx;
import java.util.concurrent.Executor;

public final class zzv {
    private static final zzw zza;
    private static zzw zzb;

    static {
        zzw zzw2;
        zza = zzw2 = zzx.zza;
        zzb = zzw2;
    }

    public static Executor zza(Executor executor) {
        return zzb.zza(executor);
    }

    static final /* synthetic */ Executor zzb(Executor executor) {
        return executor;
    }
}

