/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.common.zza;
import com.google.android.gms.internal.common.zzb;

public interface ICancelToken
extends IInterface {
    public void cancel() throws RemoteException;

    public static abstract class Stub
    extends com.google.android.gms.internal.common.zza
    implements ICancelToken {
        public Stub() {
            super("com.google.android.gms.common.internal.ICancelToken");
        }

        public static ICancelToken asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICancelToken");
            if (!(iInterface instanceof ICancelToken)) return new zza(iBinder);
            return (ICancelToken)iInterface;
        }

        public static final class zza
        extends zzb
        implements ICancelToken {
            zza(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.common.internal.ICancelToken");
            }

            @Override
            public final void cancel() throws RemoteException {
                this.zzc(2, this.a_());
            }
        }

    }

}

