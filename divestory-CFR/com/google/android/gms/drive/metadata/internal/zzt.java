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

public class zzt
extends zza<String> {
    public zzt(String string2, int n) {
        super(string2, n);
    }

    @Override
    protected final /* synthetic */ void zza(Bundle bundle, Object object) {
        object = (String)object;
        bundle.putString(this.getName(), (String)object);
    }

    @Override
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return bundle.getString(this.getName());
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return dataHolder.getString(this.getName(), n, n2);
    }
}

