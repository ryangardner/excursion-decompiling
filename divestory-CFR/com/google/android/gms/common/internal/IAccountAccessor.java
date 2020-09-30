/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.common.zza;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzd;

public interface IAccountAccessor
extends IInterface {
    public Account zza() throws RemoteException;

    public static abstract class Stub
    extends com.google.android.gms.internal.common.zza
    implements IAccountAccessor {
        public Stub() {
            super("com.google.android.gms.common.internal.IAccountAccessor");
        }

        public static IAccountAccessor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
            if (!(iInterface instanceof IAccountAccessor)) return new zza(iBinder);
            return (IAccountAccessor)iInterface;
        }

        @Override
        protected final boolean zza(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 2) return false;
            parcel = this.zza();
            parcel2.writeNoException();
            zzd.zzb(parcel2, (Parcelable)parcel);
            return true;
        }

        public static final class zza
        extends zzb
        implements IAccountAccessor {
            zza(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.common.internal.IAccountAccessor");
            }

            @Override
            public final Account zza() throws RemoteException {
                Parcel parcel = this.zza(2, this.a_());
                Account account = (Account)zzd.zza(parcel, Account.CREATOR);
                parcel.recycle();
                return account;
            }
        }

    }

}

