/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.zzr;
import com.google.android.gms.internal.drive.zzem;
import java.util.ArrayList;
import java.util.List;

public final class zzen
implements Parcelable.Creator<zzem> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList<zzr> arrayList = null;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzem(arrayList, n2);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 2) {
                if (n4 != 3) {
                    SafeParcelReader.skipUnknownField(parcel, n3);
                    continue;
                }
                n2 = SafeParcelReader.readInt(parcel, n3);
                continue;
            }
            arrayList = SafeParcelReader.createTypedList(parcel, n3, zzr.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzem[n];
    }
}

