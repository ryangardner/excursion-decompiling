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
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.common.zzb;

public final class zzn
extends zzb
implements zzm {
    zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ICertData");
    }

    @Override
    public final IObjectWrapper zzb() throws RemoteException {
        Parcel parcel = this.zza(1, this.a_());
        IObjectWrapper iObjectWrapper = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
        parcel.recycle();
        return iObjectWrapper;
    }

    @Override
    public final int zzc() throws RemoteException {
        Parcel parcel = this.zza(2, this.a_());
        int n = parcel.readInt();
        parcel.recycle();
        return n;
    }
}

