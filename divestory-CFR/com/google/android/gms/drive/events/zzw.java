/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.events.zzv;
import com.google.android.gms.internal.drive.zzh;
import java.util.ArrayList;
import java.util.List;

public final class zzw
implements Parcelable.Creator<zzv> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList<zzh> arrayList = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzv(arrayList);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            if (SafeParcelReader.getFieldId(n2) != 3) {
                SafeParcelReader.skipUnknownField(parcel, n2);
                continue;
            }
            arrayList = SafeParcelReader.createTypedList(parcel, n2, zzh.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzv[n];
    }
}

