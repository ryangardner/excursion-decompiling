/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zza
implements IInterface {
    private final IBinder zza;
    private final String zzb;

    protected zza(IBinder iBinder, String string2) {
        this.zza = iBinder;
        this.zzb = string2;
    }

    public IBinder asBinder() {
        return this.zza;
    }

    protected final Parcel obtainAndWriteInterfaceToken() {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(this.zzb);
        return parcel;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    protected final Parcel transactAndReadException(int n, Parcel parcel) throws RemoteException {
        Throwable throwable2222;
        Parcel parcel2 = Parcel.obtain();
        this.zza.transact(n, parcel, parcel2, 0);
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

    protected final void transactAndReadExceptionReturnVoid(int n, Parcel parcel) throws RemoteException {
        Parcel parcel2 = Parcel.obtain();
        try {
            this.zza.transact(n, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    protected final void transactOneway(int n, Parcel parcel) throws RemoteException {
        try {
            this.zza.transact(n, parcel, null, 1);
            return;
        }
        finally {
            parcel.recycle();
        }
    }
}

