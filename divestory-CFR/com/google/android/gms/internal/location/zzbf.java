/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzal;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.internal.location.zzbg;
import com.google.android.gms.location.zzu;
import com.google.android.gms.location.zzv;
import com.google.android.gms.location.zzx;
import com.google.android.gms.location.zzy;

public final class zzbf
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbf> CREATOR = new zzbg();
    private PendingIntent zzbv;
    private int zzcg;
    private zzaj zzcj;
    private zzbd zzdl;
    private zzx zzdm;
    private zzu zzdn;

    zzbf(int n, zzbd object, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2, IBinder iBinder3) {
        this.zzcg = n;
        this.zzdl = object;
        Object var7_7 = null;
        object = iBinder == null ? null : zzy.zzc(iBinder);
        this.zzdm = object;
        this.zzbv = pendingIntent;
        object = iBinder2 == null ? null : zzv.zzb(iBinder2);
        this.zzdn = object;
        object = iBinder3 == null ? var7_7 : (iBinder3 == null ? var7_7 : ((object = iBinder3.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback")) instanceof zzaj ? (zzaj)object : new zzal(iBinder3)));
        this.zzcj = object;
    }

    public static zzbf zza(zzu zzu2, zzaj zzaj2) {
        IBinder iBinder = zzu2.asBinder();
        if (zzaj2 != null) {
            zzu2 = zzaj2.asBinder();
            return new zzbf(2, null, null, null, iBinder, (IBinder)zzu2);
        }
        zzu2 = null;
        return new zzbf(2, null, null, null, iBinder, (IBinder)zzu2);
    }

    public static zzbf zza(zzx zzx2, zzaj zzaj2) {
        IBinder iBinder = zzx2.asBinder();
        if (zzaj2 != null) {
            zzx2 = zzaj2.asBinder();
            return new zzbf(2, null, iBinder, null, null, (IBinder)zzx2);
        }
        zzx2 = null;
        return new zzbf(2, null, iBinder, null, null, (IBinder)zzx2);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzcg);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdl, n, false);
        Object object = this.zzdm;
        Object var5_5 = null;
        object = object == null ? null : object.asBinder();
        SafeParcelWriter.writeIBinder(parcel, 3, (IBinder)object, false);
        SafeParcelWriter.writeParcelable(parcel, 4, (Parcelable)this.zzbv, n, false);
        object = this.zzdn;
        object = object == null ? null : object.asBinder();
        SafeParcelWriter.writeIBinder(parcel, 5, (IBinder)object, false);
        object = this.zzcj;
        object = object == null ? var5_5 : object.asBinder();
        SafeParcelWriter.writeIBinder(parcel, 6, (IBinder)object, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

