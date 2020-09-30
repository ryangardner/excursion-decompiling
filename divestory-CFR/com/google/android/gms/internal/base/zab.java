/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.base;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zab
implements IInterface {
    private final IBinder zaa;
    private final String zab;

    protected zab(IBinder iBinder, String string2) {
        this.zaa = iBinder;
        this.zab = string2;
    }

    public IBinder asBinder() {
        return this.zaa;
    }

    protected final Parcel zaa() {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(this.zab);
        return parcel;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    protected final Parcel zaa(int n, Parcel parcel) throws RemoteException {
        Throwable throwable2222;
        Parcel parcel2 = Parcel.obtain();
        this.zaa.transact(2, parcel, parcel2, 0);
        parcel2.readException();
        parcel.recycle();
        return parcel2;
        {
            catch (Throwable throwable2222) {
            }
            catch (RuntimeException runtimeException) {}
            {
                parcel2.recycle();
                throw runtimeException;
            }
        }
        parcel.recycle();
        throw throwable2222;
    }

    protected final void zab(int n, Parcel parcel) throws RemoteException {
        Parcel parcel2 = Parcel.obtain();
        try {
            this.zaa.transact(n, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    protected final void zac(int n, Parcel parcel) throws RemoteException {
        try {
            this.zaa.transact(1, parcel, null, 1);
            return;
        }
        finally {
            parcel.recycle();
        }
    }
}

