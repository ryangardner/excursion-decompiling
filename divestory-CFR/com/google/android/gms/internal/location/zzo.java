/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzal;
import com.google.android.gms.internal.location.zzm;
import com.google.android.gms.internal.location.zzp;
import com.google.android.gms.location.zzr;
import com.google.android.gms.location.zzs;

public final class zzo
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzo> CREATOR = new zzp();
    private int zzcg;
    private zzm zzch;
    private zzr zzci;
    private zzaj zzcj;

    zzo(int n, zzm object, IBinder iBinder, IBinder iBinder2) {
        this.zzcg = n;
        this.zzch = object;
        Object var5_5 = null;
        object = iBinder == null ? null : zzs.zza(iBinder);
        this.zzci = object;
        object = iBinder2 == null ? var5_5 : (iBinder2 == null ? var5_5 : ((object = iBinder2.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback")) instanceof zzaj ? (zzaj)object : new zzal(iBinder2)));
        this.zzcj = object;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzcg);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzch, n, false);
        Object object = this.zzci;
        Object var5_5 = null;
        object = object == null ? null : object.asBinder();
        SafeParcelWriter.writeIBinder(parcel, 3, (IBinder)object, false);
        object = this.zzcj;
        object = object == null ? var5_5 : object.asBinder();
        SafeParcelWriter.writeIBinder(parcel, 4, (IBinder)object, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

