/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzj;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzd;

public final class zzs
extends zzb
implements zzr {
    zzs(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    @Override
    public final boolean zza(zzj zzj2, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, zzj2);
        zzd.zza(parcel, iObjectWrapper);
        zzj2 = this.zza(5, parcel);
        boolean bl = zzd.zza((Parcel)zzj2);
        zzj2.recycle();
        return bl;
    }
}

