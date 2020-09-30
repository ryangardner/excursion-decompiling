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
import com.google.android.gms.drive.query.internal.zzi;
import com.google.android.gms.drive.query.internal.zzj;
import com.google.android.gms.drive.query.internal.zzo;

public final class zzn<T>
extends zza {
    public static final zzo CREATOR = new zzo();
    private final MetadataBundle zzma;
    private final MetadataField<T> zzmb;

    public zzn(SearchableMetadataField<T> searchableMetadataField, T t) {
        this(MetadataBundle.zza(searchableMetadataField, t));
    }

    zzn(MetadataBundle metadataBundle) {
        this.zzma = metadataBundle;
        this.zzmb = zzi.zza(metadataBundle);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzma, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final <F> F zza(zzj<F> zzj2) {
        MetadataField<T> metadataField = this.zzmb;
        return zzj2.zzc(metadataField, this.zzma.zza(metadataField));
    }
}

