/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.zzd;
import com.google.android.gms.common.zzg;
import com.google.android.gms.common.zzm;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import javax.annotation.Nullable;

public final class zzj
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzj> CREATOR = new zzm();
    private final String zza;
    @Nullable
    private final zzd zzb;
    private final boolean zzc;
    private final boolean zzd;

    zzj(String string2, @Nullable IBinder iBinder, boolean bl, boolean bl2) {
        this.zza = string2;
        this.zzb = zzj.zza(iBinder);
        this.zzc = bl;
        this.zzd = bl2;
    }

    zzj(String string2, @Nullable zzd zzd2, boolean bl, boolean bl2) {
        this.zza = string2;
        this.zzb = zzd2;
        this.zzc = bl;
        this.zzd = bl2;
    }

    @Nullable
    private static zzd zza(@Nullable IBinder object) {
        Object var1_2 = null;
        if (object == null) {
            return null;
        }
        try {
            object = zzo.zza((IBinder)object).zzb();
            object = object == null ? null : (byte[])ObjectWrapper.unwrap((IObjectWrapper)object);
        }
        catch (RemoteException remoteException) {
            Log.e((String)"GoogleCertificatesQuery", (String)"Could not unwrap certificate", (Throwable)remoteException);
            return null;
        }
        if (object != null) {
            return new zzg((byte[])object);
        }
        Log.e((String)"GoogleCertificatesQuery", (String)"Could not unwrap certificate");
        return var1_2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        zzd zzd2 = this.zzb;
        if (zzd2 == null) {
            Log.w((String)"GoogleCertificatesQuery", (String)"certificate binder is null");
            zzd2 = null;
        } else {
            zzd2 = zzd2.asBinder();
        }
        SafeParcelWriter.writeIBinder(parcel, 2, (IBinder)zzd2, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzc);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzd);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

