/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzn;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.common.zza;
import com.google.android.gms.internal.common.zzd;

public abstract class zzo
extends zza
implements zzm {
    public zzo() {
        super("com.google.android.gms.common.internal.ICertData");
    }

    public static zzm zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
        if (!(iInterface instanceof zzm)) return new zzn(iBinder);
        return (zzm)iInterface;
    }

    @Override
    protected final boolean zza(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (n == 1) {
            object = this.zzb();
            parcel.writeNoException();
            zzd.zza(parcel, (IInterface)object);
            return true;
        }
        if (n != 2) {
            return false;
        }
        n = this.zzc();
        parcel.writeNoException();
        parcel.writeInt(n);
        return true;
    }
}

