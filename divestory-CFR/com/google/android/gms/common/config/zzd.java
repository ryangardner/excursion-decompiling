/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.config;

import com.google.android.gms.common.config.GservicesValue;
import com.google.android.gms.common.internal.Preconditions;

final class zzd
extends GservicesValue<Integer> {
    zzd(String string2, Integer n) {
        super(string2, n);
    }

    @Override
    protected final /* synthetic */ Object zza(String string2) {
        return ((GservicesValue.zza)Preconditions.checkNotNull(null)).zza(this.zza, (Integer)this.zzb);
    }
}

