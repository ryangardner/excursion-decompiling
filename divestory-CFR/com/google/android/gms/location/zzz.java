/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.location.Location;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zza;
import com.google.android.gms.internal.location.zzc;
import com.google.android.gms.location.zzx;

public final class zzz
extends zza
implements zzx {
    zzz(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.ILocationListener");
    }

    @Override
    public final void onLocationChanged(Location location) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, (Parcelable)location);
        this.transactOneway(1, parcel);
    }
}

