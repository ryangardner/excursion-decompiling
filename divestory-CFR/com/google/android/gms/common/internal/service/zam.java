/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.service.zai;
import com.google.android.gms.common.internal.service.zak;
import com.google.android.gms.internal.base.zab;
import com.google.android.gms.internal.base.zad;

public final class zam
extends zab
implements zak {
    zam(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.service.ICommonService");
    }

    @Override
    public final void zaa(zai zai2) throws RemoteException {
        Parcel parcel = this.zaa();
        zad.zaa(parcel, zai2);
        this.zac(1, parcel);
    }
}

