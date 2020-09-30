/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.config;

import com.google.android.gms.common.config.GservicesValue;
import com.google.android.gms.common.internal.Preconditions;

final class zzc
extends GservicesValue<Float> {
    zzc(String string2, Float f) {
        super(string2, f);
    }

    @Override
    protected final /* synthetic */ Object zza(String string2) {
        return ((GservicesValue.zza)Preconditions.checkNotNull(null)).zza(this.zza, (Float)this.zzb);
    }
}

