/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamite.zzm;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzd;

public final class zzl
extends zzb
implements zzm {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    @Override
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String object, int n, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, iObjectWrapper);
        parcel.writeString((String)object);
        parcel.writeInt(n);
        zzd.zza(parcel, iObjectWrapper2);
        iObjectWrapper = this.zza(2, parcel);
        object = IObjectWrapper.Stub.asInterface(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return object;
    }

    @Override
    public final IObjectWrapper zzb(IObjectWrapper iObjectWrapper, String object, int n, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, iObjectWrapper);
        parcel.writeString((String)object);
        parcel.writeInt(n);
        zzd.zza(parcel, iObjectWrapper2);
        iObjectWrapper = this.zza(3, parcel);
        object = IObjectWrapper.Stub.asInterface(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return object;
    }
}

