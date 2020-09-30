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
import java.util.Collection;

public class zzb
extends zza<Boolean> {
    public zzb(String string2, int n) {
        super(string2, n);
    }

    public zzb(String string2, Collection<String> collection, Collection<String> collection2, int n) {
        super(string2, collection, collection2, 7000000);
    }

    @Override
    protected final /* synthetic */ void zza(Bundle bundle, Object object) {
        object = (Boolean)object;
        bundle.putBoolean(this.getName(), ((Boolean)object).booleanValue());
    }

    @Override
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return bundle.getBoolean(this.getName());
    }

    @Override
    protected /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return this.zze(dataHolder, n, n2);
    }

    protected Boolean zze(DataHolder dataHolder, int n, int n2) {
        return dataHolder.getBoolean(this.getName(), n, n2);
    }
}

