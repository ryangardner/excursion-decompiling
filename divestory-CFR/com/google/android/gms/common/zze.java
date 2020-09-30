/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzd;
import java.util.concurrent.Callable;

final class zze
implements Callable {
    private final boolean zza;
    private final String zzb;
    private final zzd zzc;

    zze(boolean bl, String string2, zzd zzd2) {
        this.zza = bl;
        this.zzb = string2;
        this.zzc = zzd2;
    }

    public final Object call() {
        return zzc.zza(this.zza, this.zzb, this.zzc);
    }
}

