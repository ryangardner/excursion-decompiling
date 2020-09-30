/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.zza;
import com.google.android.gms.drive.query.internal.zzb;
import com.google.android.gms.drive.query.internal.zzd;
import com.google.android.gms.drive.query.internal.zzh;
import com.google.android.gms.drive.query.internal.zzl;
import com.google.android.gms.drive.query.internal.zzn;
import com.google.android.gms.drive.query.internal.zzp;
import com.google.android.gms.drive.query.internal.zzr;
import com.google.android.gms.drive.query.internal.zzt;
import com.google.android.gms.drive.query.internal.zzv;
import com.google.android.gms.drive.query.internal.zzz;

public class FilterHolder
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<FilterHolder> CREATOR = new zzh();
    private final Filter zzbc;
    private final zzb<?> zzmd;
    private final zzd zzme;
    private final zzr zzmf;
    private final zzv zzmg;
    private final zzp<?> zzmh;
    private final zzt zzmi;
    private final zzn zzmj;
    private final zzl zzmk;
    private final zzz zzml;

    public FilterHolder(Filter filter) {
        Preconditions.checkNotNull(filter, "Null filter.");
        boolean bl = filter instanceof zzb;
        java.lang.Object var3_3 = null;
        zza zza2 = bl ? (zzb)filter : null;
        this.zzmd = zza2;
        zza2 = filter instanceof zzd ? (zzd)filter : null;
        this.zzme = zza2;
        zza2 = filter instanceof zzr ? (zzr)filter : null;
        this.zzmf = zza2;
        zza2 = filter instanceof zzv ? (zzv)filter : null;
        this.zzmg = zza2;
        zza2 = filter instanceof zzp ? (zzp)filter : null;
        this.zzmh = zza2;
        zza2 = filter instanceof zzt ? (zzt)filter : null;
        this.zzmi = zza2;
        zza2 = filter instanceof zzn ? (zzn)filter : null;
        this.zzmj = zza2;
        zza2 = filter instanceof zzl ? (zzl)filter : null;
        this.zzmk = zza2;
        zza2 = var3_3;
        if (filter instanceof zzz) {
            zza2 = (zzz)filter;
        }
        this.zzml = zza2;
        if (this.zzmd == null && this.zzme == null && this.zzmf == null && this.zzmg == null && this.zzmh == null && this.zzmi == null && this.zzmj == null && this.zzmk == null) {
            if (zza2 == null) throw new IllegalArgumentException("Invalid filter type.");
        }
        this.zzbc = filter;
    }

    FilterHolder(zzb<?> zzb2, zzd zzd2, zzr zzr2, zzv zzv2, zzp<?> zzp2, zzt zzt2, zzn<?> zzn2, zzl zzl2, zzz zzz2) {
        this.zzmd = zzb2;
        this.zzme = zzd2;
        this.zzmf = zzr2;
        this.zzmg = zzv2;
        this.zzmh = zzp2;
        this.zzmi = zzt2;
        this.zzmj = zzn2;
        this.zzmk = zzl2;
        this.zzml = zzz2;
        if (zzb2 != null) {
            this.zzbc = zzb2;
            return;
        }
        if (zzd2 != null) {
            this.zzbc = zzd2;
            return;
        }
        if (zzr2 != null) {
            this.zzbc = zzr2;
            return;
        }
        if (zzv2 != null) {
            this.zzbc = zzv2;
            return;
        }
        if (zzp2 != null) {
            this.zzbc = zzp2;
            return;
        }
        if (zzt2 != null) {
            this.zzbc = zzt2;
            return;
        }
        if (zzn2 != null) {
            this.zzbc = zzn2;
            return;
        }
        if (zzl2 != null) {
            this.zzbc = zzl2;
            return;
        }
        if (zzz2 == null) throw new IllegalArgumentException("At least one filter must be set.");
        this.zzbc = zzz2;
    }

    public final Filter getFilter() {
        return this.zzbc;
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzmd, n, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzme, n, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzmf, n, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzmg, n, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzmh, n, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzmi, n, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzmj, n, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzmk, n, false);
        SafeParcelWriter.writeParcelable(parcel, 9, this.zzml, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

