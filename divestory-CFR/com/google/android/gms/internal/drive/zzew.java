/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.drive.zza;
import com.google.android.gms.internal.drive.zzc;
import com.google.android.gms.internal.drive.zzeu;

public final class zzew
extends zza
implements zzeu {
    zzew(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.drive.internal.IEventReleaseCallback");
    }

    @Override
    public final void zza(boolean bl) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.writeBoolean(parcel, bl);
        this.zzc(1, parcel);
    }
}

