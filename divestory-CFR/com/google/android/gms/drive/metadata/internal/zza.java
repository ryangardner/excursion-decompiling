/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.drive.metadata.internal.zzc;
import java.util.ArrayList;
import java.util.Collection;

public final class zza
implements Parcelable.Creator<AppVisibleCustomProperties> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList<zzc> arrayList = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new AppVisibleCustomProperties(arrayList);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            if (SafeParcelReader.getFieldId(n2) != 2) {
                SafeParcelReader.skipUnknownField(parcel, n2);
                continue;
            }
            arrayList = SafeParcelReader.createTypedList(parcel, n2, zzc.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new AppVisibleCustomProperties[n];
    }
}

