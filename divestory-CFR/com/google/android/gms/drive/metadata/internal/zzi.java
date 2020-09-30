/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.zza;

public class zzi
extends zza<Long> {
    public zzi(String string2, int n) {
        super(string2, 4300000);
    }

    @Override
    protected final /* synthetic */ void zza(Bundle bundle, Object object) {
        object = (Long)object;
        bundle.putLong(this.getName(), ((Long)object).longValue());
    }

    @Override
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return bundle.getLong(this.getName());
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return dataHolder.getLong(this.getName(), n, n2);
    }
}

