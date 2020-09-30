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
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.internal.zza;
import com.google.android.gms.drive.query.internal.zze;
import com.google.android.gms.drive.query.internal.zzi;
import com.google.android.gms.drive.query.internal.zzj;

public final class zzd
extends zza {
    public static final Parcelable.Creator<zzd> CREATOR = new zze();
    private final MetadataBundle zzma;
    private final MetadataField<?> zzmb;

    public zzd(SearchableMetadataField<?> searchableMetadataField) {
        this(MetadataBundle.zza(searchableMetadataField, null));
    }

    zzd(MetadataBundle metadataBundle) {
        this.zzma = metadataBundle;
        this.zzmb = zzi.zza(metadataBundle);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzma, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    @Override
    public final <T> T zza(zzj<T> zzj2) {
        return zzj2.zze(this.zzmb);
    }
}

