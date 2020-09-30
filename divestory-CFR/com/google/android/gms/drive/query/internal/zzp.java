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
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.metadata.zzb;
import com.google.android.gms.drive.query.internal.zza;
import com.google.android.gms.drive.query.internal.zzi;
import com.google.android.gms.drive.query.internal.zzj;
import com.google.android.gms.drive.query.internal.zzq;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class zzp<T>
extends zza {
    public static final zzq CREATOR = new zzq();
    private final MetadataBundle zzma;
    private final zzb<T> zzmn;

    public zzp(SearchableCollectionMetadataField<T> searchableCollectionMetadataField, T t) {
        this(MetadataBundle.zza(searchableCollectionMetadataField, Collections.singleton(t)));
    }

    zzp(MetadataBundle metadataBundle) {
        this.zzma = metadataBundle;
        this.zzmn = (zzb)zzi.zza(metadataBundle);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzma, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final <F> F zza(zzj<F> zzj2) {
        zzb<T> zzb2 = this.zzmn;
        return zzj2.zza(zzb2, ((Collection)this.zzma.zza(zzb2)).iterator().next());
    }
}

