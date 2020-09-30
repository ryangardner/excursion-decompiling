/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zzam;
import com.google.android.gms.internal.location.zzb;
import com.google.android.gms.internal.location.zzc;

public abstract class zzan
extends zzb
implements zzam {
    public zzan() {
        super("com.google.android.gms.location.internal.IGeofencerCallbacks");
    }

    @Override
    protected final boolean dispatchTransaction(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (n == 1) {
            this.zza(parcel.readInt(), parcel.createStringArray());
            return true;
        }
        if (n == 2) {
            this.zzb(parcel.readInt(), parcel.createStringArray());
            return true;
        }
        if (n != 3) {
            return false;
        }
        this.zza(parcel.readInt(), (PendingIntent)zzc.zza(parcel, PendingIntent.CREATOR));
        return true;
    }
}

