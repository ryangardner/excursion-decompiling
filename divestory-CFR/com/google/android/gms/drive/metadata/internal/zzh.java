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

public final class zzh
extends zza<Integer> {
    public zzh(String string2, int n) {
        super(string2, 4300000);
    }

    @Override
    protected final /* synthetic */ void zza(Bundle bundle, Object object) {
        object = (Integer)object;
        bundle.putInt(this.getName(), ((Integer)object).intValue());
    }

    @Override
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return bundle.getInt(this.getName());
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return dataHolder.getInteger(this.getName(), n, n2);
    }
}

