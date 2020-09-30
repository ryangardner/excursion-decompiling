/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zam;
import com.google.android.gms.common.internal.zau;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.base.zab;
import com.google.android.gms.internal.base.zad;

public final class zal
extends zab
implements zam {
    zal(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ISignInButtonCreator");
    }

    @Override
    public final IObjectWrapper zaa(IObjectWrapper iObjectWrapper, zau object) throws RemoteException {
        Parcel parcel = this.zaa();
        zad.zaa(parcel, iObjectWrapper);
        zad.zaa(parcel, (Parcelable)object);
        iObjectWrapper = this.zaa(2, parcel);
        object = IObjectWrapper.Stub.asInterface(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return object;
    }
}

