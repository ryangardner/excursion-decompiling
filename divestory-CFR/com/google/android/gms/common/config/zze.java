/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.config;

import com.google.android.gms.common.config.GservicesValue;
import com.google.android.gms.common.internal.Preconditions;

final class zze
extends GservicesValue<String> {
    zze(String string2, String string3) {
        super(string2, string3);
    }

    @Override
    protected final /* synthetic */ Object zza(String string2) {
        return ((GservicesValue.zza)Preconditions.checkNotNull(null)).zza(this.zza, (String)this.zzb);
    }
}

