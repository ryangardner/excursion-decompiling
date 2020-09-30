/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zzb;
import com.google.android.gms.internal.location.zzc;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzu;
import com.google.android.gms.location.zzw;

public abstract class zzv
extends zzb
implements zzu {
    public zzv() {
        super("com.google.android.gms.location.ILocationCallback");
    }

    public static zzu zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.ILocationCallback");
        if (!(iInterface instanceof zzu)) return new zzw(iBinder);
        return (zzu)iInterface;
    }

    @Override
    protected final boolean dispatchTransaction(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (n == 1) {
            this.onLocationResult(zzc.zza(parcel, LocationResult.CREATOR));
            return true;
        }
        if (n != 2) {
            return false;
        }
        this.onLocationAvailability(zzc.zza(parcel, LocationAvailability.CREATOR));
        return true;
    }
}

