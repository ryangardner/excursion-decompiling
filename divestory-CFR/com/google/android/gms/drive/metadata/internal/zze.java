/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.zzd;
import java.util.Date;

public class zze
extends zzd<Date> {
    public zze(String string2, int n) {
        super(string2, n);
    }

    @Override
    protected final /* synthetic */ void zza(Bundle bundle, Object object) {
        object = (Date)object;
        bundle.putLong(this.getName(), ((Date)object).getTime());
    }

    @Override
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return new Date(bundle.getLong(this.getName()));
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return new Date(dataHolder.getLong(this.getName(), n, n2));
    }
}

