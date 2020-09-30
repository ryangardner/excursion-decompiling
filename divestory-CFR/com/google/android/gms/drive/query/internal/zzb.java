/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.internal.zza;
import com.google.android.gms.drive.query.internal.zzc;
import com.google.android.gms.drive.query.internal.zzi;
import com.google.android.gms.drive.query.internal.zzj;
import com.google.android.gms.drive.query.internal.zzx;

public final class zzb<T>
extends zza {
    public static final zzc CREATOR = new zzc();
    private final zzx zzlz;
    private final MetadataBundle zzma;
    private final MetadataField<T> zzmb;

    public zzb(zzx zzx2, SearchableMetadataField<T> searchableMetadataField, T t) {
        this(zzx2, MetadataBundle.zza(searchableMetadataField, t));
    }

    zzb(zzx zzx2, MetadataBundle metadataBundle) {
        this.zzlz = zzx2;
        this.zzma = metadataBundle;
        this.zzmb = zzi.zza(metadataBundle);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzlz, n, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzma, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final <F> F zza(zzj<F> zzj2) {
        zzx zzx2 = this.zzlz;
        MetadataField<T> metadataField = this.zzmb;
        return zzj2.zza(zzx2, metadataField, this.zzma.zza(metadataField));
    }
}

