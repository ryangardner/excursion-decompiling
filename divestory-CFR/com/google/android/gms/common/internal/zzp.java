/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IGmsCallbacks;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzd;

public final class zzp
extends zzb
implements IGmsCallbacks {
    zzp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGmsCallbacks");
    }

    @Override
    public final void onPostInitComplete(int n, IBinder iBinder, Bundle bundle) throws RemoteException {
        Parcel parcel = this.a_();
        parcel.writeInt(n);
        parcel.writeStrongBinder(iBinder);
        zzd.zza(parcel, (Parcelable)bundle);
        this.zzb(1, parcel);
    }

    @Override
    public final void zza(int n, Bundle bundle) throws RemoteException {
        Parcel parcel = this.a_();
        parcel.writeInt(n);
        zzd.zza(parcel, (Parcelable)bundle);
        this.zzb(2, parcel);
    }

    @Override
    public final void zza(int n, IBinder iBinder, zzc zzc2) throws RemoteException {
        Parcel parcel = this.a_();
        parcel.writeInt(n);
        parcel.writeStrongBinder(iBinder);
        zzd.zza(parcel, zzc2);
        this.zzb(3, parcel);
    }
}

