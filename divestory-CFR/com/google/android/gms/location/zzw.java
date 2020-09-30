/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zza;
import com.google.android.gms.internal.location.zzc;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzu;

public final class zzw
extends zza
implements zzu {
    zzw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.ILocationCallback");
    }

    @Override
    public final void onLocationAvailability(LocationAvailability locationAvailability) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, locationAvailability);
        this.transactOneway(2, parcel);
    }

    @Override
    public final void onLocationResult(LocationResult locationResult) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, locationResult);
        this.transactOneway(1, parcel);
    }
}

