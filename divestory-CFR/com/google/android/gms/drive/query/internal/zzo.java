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
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.internal.zzn;

public final class zzo
implements Parcelable.Creator<zzn> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        MetadataBundle metadataBundle = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzn(metadataBundle);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            if (SafeParcelReader.getFieldId(n2) != 1) {
                SafeParcelReader.skipUnknownField(parcel, n2);
                continue;
            }
            metadataBundle = SafeParcelReader.createParcelable(parcel, n2, MetadataBundle.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzn[n];
    }
}

