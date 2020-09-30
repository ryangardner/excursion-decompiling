/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.base;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.base.zac;

public class zaa
extends Binder
implements IInterface {
    private static zac zaa;

    protected zaa(String string2) {
        this.attachInterface((IInterface)this, string2);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        boolean bl;
        if (n > 16777215) {
            bl = super.onTransact(n, parcel, parcel2, n2);
        } else {
            parcel.enforceInterface(this.getInterfaceDescriptor());
            bl = false;
        }
        if (!bl) return this.zaa(n, parcel, parcel2, n2);
        return true;
    }

    protected boolean zaa(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        return false;
    }
}

