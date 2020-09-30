/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.base.zaa;
import com.google.android.gms.internal.base.zab;
import com.google.android.gms.internal.base.zad;

public interface IStatusCallback
extends IInterface {
    public void onResult(Status var1) throws RemoteException;

    public static abstract class Stub
    extends com.google.android.gms.internal.base.zaa
    implements IStatusCallback {
        public Stub() {
            super("com.google.android.gms.common.api.internal.IStatusCallback");
        }

        public static IStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.api.internal.IStatusCallback");
            if (!(iInterface instanceof IStatusCallback)) return new zaa(iBinder);
            return (IStatusCallback)iInterface;
        }

        @Override
        protected final boolean zaa(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) return false;
            this.onResult(zad.zaa(parcel, Status.CREATOR));
            return true;
        }

        public static final class zaa
        extends zab
        implements IStatusCallback {
            zaa(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.common.api.internal.IStatusCallback");
            }

            @Override
            public final void onResult(Status status) throws RemoteException {
                Parcel parcel = this.zaa();
                zad.zaa(parcel, status);
                this.zac(1, parcel);
            }
        }

    }

}

