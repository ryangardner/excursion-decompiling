/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzb
implements DynamiteModule.VersionPolicy.zzb {
    zzb() {
    }

    @Override
    public final int zza(Context context, String string2) {
        return DynamiteModule.getLocalVersion(context, string2);
    }

    @Override
    public final int zza(Context context, String string2, boolean bl) throws DynamiteModule.LoadingException {
        return DynamiteModule.zza(context, string2, bl);
    }
}

