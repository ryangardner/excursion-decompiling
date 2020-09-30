/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.config;

import com.google.android.gms.common.config.GservicesValue;
import com.google.android.gms.common.internal.Preconditions;

final class zzb
extends GservicesValue<Boolean> {
    zzb(String string2, Boolean bl) {
        super(string2, bl);
    }

    @Override
    protected final /* synthetic */ Object zza(String string2) {
        return ((GservicesValue.zza)Preconditions.checkNotNull(null)).zza(this.zza, (Boolean)this.zzb);
    }
}

