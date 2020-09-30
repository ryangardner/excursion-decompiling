/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.drive.zzb;
import com.google.android.gms.internal.drive.zzc;
import com.google.android.gms.internal.drive.zzes;
import com.google.android.gms.internal.drive.zzfp;

public abstract class zzet
extends zzb
implements zzes {
    public zzet() {
        super("com.google.android.gms.drive.internal.IEventCallback");
    }

    @Override
    protected final boolean dispatchTransaction(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (n != 1) return false;
        this.zzc(zzc.zza(parcel, zzfp.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}

