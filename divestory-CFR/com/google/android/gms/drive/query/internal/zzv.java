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
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.query.internal.zza;
import com.google.android.gms.drive.query.internal.zzj;
import com.google.android.gms.drive.query.internal.zzw;

public final class zzv
extends zza {
    public static final Parcelable.Creator<zzv> CREATOR = new zzw();
    private final FilterHolder zzmp;

    public zzv(Filter filter) {
        this(new FilterHolder(filter));
    }

    zzv(FilterHolder filterHolder) {
        this.zzmp = filterHolder;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzmp, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    @Override
    public final <T> T zza(zzj<T> zzj2) {
        return zzj2.zza(this.zzmp.getFilter().zza(zzj2));
    }
}

