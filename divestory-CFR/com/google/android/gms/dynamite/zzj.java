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
import com.google.android.gms.dynamite.zzk;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzd;

public final class zzj
extends zzb
implements zzk {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
    }

    @Override
    public final int zza(IObjectWrapper iObjectWrapper, String string2, boolean bl) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, iObjectWrapper);
        parcel.writeString(string2);
        zzd.zza(parcel, bl);
        iObjectWrapper = this.zza(3, parcel);
        int n = iObjectWrapper.readInt();
        iObjectWrapper.recycle();
        return n;
    }

    @Override
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String string2, int n) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, iObjectWrapper);
        parcel.writeString(string2);
        parcel.writeInt(n);
        string2 = this.zza(2, parcel);
        iObjectWrapper = IObjectWrapper.Stub.asInterface(string2.readStrongBinder());
        string2.recycle();
        return iObjectWrapper;
    }

    @Override
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String string2, int n, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, iObjectWrapper);
        parcel.writeString(string2);
        parcel.writeInt(n);
        zzd.zza(parcel, iObjectWrapper2);
        string2 = this.zza(8, parcel);
        iObjectWrapper = IObjectWrapper.Stub.asInterface(string2.readStrongBinder());
        string2.recycle();
        return iObjectWrapper;
    }

    @Override
    public final int zzb() throws RemoteException {
        Parcel parcel = this.zza(6, this.a_());
        int n = parcel.readInt();
        parcel.recycle();
        return n;
    }

    @Override
    public final int zzb(IObjectWrapper iObjectWrapper, String string2, boolean bl) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, iObjectWrapper);
        parcel.writeString(string2);
        zzd.zza(parcel, bl);
        iObjectWrapper = this.zza(5, parcel);
        int n = iObjectWrapper.readInt();
        iObjectWrapper.recycle();
        return n;
    }

    @Override
    public final IObjectWrapper zzb(IObjectWrapper iObjectWrapper, String object, int n) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, iObjectWrapper);
        parcel.writeString((String)object);
        parcel.writeInt(n);
        iObjectWrapper = this.zza(4, parcel);
        object = IObjectWrapper.Stub.asInterface(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return object;
    }

    @Override
    public final IObjectWrapper zzc(IObjectWrapper iObjectWrapper, String string2, boolean bl) throws RemoteException {
        Parcel parcel = this.a_();
        zzd.zza(parcel, iObjectWrapper);
        parcel.writeString(string2);
        zzd.zza(parcel, bl);
        string2 = this.zza(7, parcel);
        iObjectWrapper = IObjectWrapper.Stub.asInterface(string2.readStrongBinder());
        string2.recycle();
        return iObjectWrapper;
    }
}

