/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal.service;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.service.zai;
import com.google.android.gms.internal.base.zaa;

public abstract class zal
extends zaa
implements zai {
    public zal() {
        super("com.google.android.gms.common.internal.service.ICommonCallbacks");
    }

    @Override
    protected final boolean zaa(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (n != 1) return false;
        this.zaa(parcel.readInt());
        parcel2.writeNoException();
        return true;
    }
}

