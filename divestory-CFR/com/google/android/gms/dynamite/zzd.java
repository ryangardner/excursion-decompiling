/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzd
implements DynamiteModule.VersionPolicy {
    zzd() {
    }

    @Override
    public final DynamiteModule.VersionPolicy.zza zza(Context context, String string2, DynamiteModule.VersionPolicy.zzb zzb2) throws DynamiteModule.LoadingException {
        DynamiteModule.VersionPolicy.zza zza2 = new DynamiteModule.VersionPolicy.zza();
        zza2.zza = zzb2.zza(context, string2);
        if (zza2.zza != 0) {
            zza2.zzc = -1;
            return zza2;
        }
        zza2.zzb = zzb2.zza(context, string2, true);
        if (zza2.zzb == 0) return zza2;
        zza2.zzc = 1;
        return zza2;
    }
}

