/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.common.zzd;

public interface IGmsCallbacks
extends IInterface {
    public void onPostInitComplete(int var1, IBinder var2, Bundle var3) throws RemoteException;

    public void zza(int var1, Bundle var2) throws RemoteException;

    public void zza(int var1, IBinder var2, zzc var3) throws RemoteException;

    public static abstract class zza
    extends com.google.android.gms.internal.common.zza
    implements IGmsCallbacks {
        public zza() {
            super("com.google.android.gms.common.internal.IGmsCallbacks");
        }

        @Override
        protected final boolean zza(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return false;
                    }
                    this.zza(parcel.readInt(), parcel.readStrongBinder(), zzd.zza(parcel, zzc.CREATOR));
                } else {
                    this.zza(parcel.readInt(), (Bundle)zzd.zza(parcel, Bundle.CREATOR));
                }
            } else {
                this.onPostInitComplete(parcel.readInt(), parcel.readStrongBinder(), (Bundle)zzd.zza(parcel, Bundle.CREATOR));
            }
            parcel2.writeNoException();
            return true;
        }
    }

}

