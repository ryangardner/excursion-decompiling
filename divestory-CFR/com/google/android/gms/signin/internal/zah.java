/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.signin.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.internal.base.zab;
import com.google.android.gms.internal.base.zad;
import com.google.android.gms.signin.internal.zac;
import com.google.android.gms.signin.internal.zae;
import com.google.android.gms.signin.internal.zak;

public final class zah
extends zab
implements zae {
    zah(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    @Override
    public final void zaa(int n) throws RemoteException {
        Parcel parcel = this.zaa();
        parcel.writeInt(n);
        this.zab(7, parcel);
    }

    @Override
    public final void zaa(IAccountAccessor iAccountAccessor, int n, boolean bl) throws RemoteException {
        Parcel parcel = this.zaa();
        zad.zaa(parcel, iAccountAccessor);
        parcel.writeInt(n);
        zad.zaa(parcel, bl);
        this.zab(9, parcel);
    }

    @Override
    public final void zaa(zak zak2, zac zac2) throws RemoteException {
        Parcel parcel = this.zaa();
        zad.zaa(parcel, zak2);
        zad.zaa(parcel, zac2);
        this.zab(12, parcel);
    }
}

