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
import com.google.android.gms.drive.zzh;
import com.google.android.gms.internal.drive.zzfl;
import java.util.ArrayList;
import java.util.List;

public final class zzfm
implements Parcelable.Creator<zzfl> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        long l2 = l = 0L;
        ArrayList<zzh> arrayList = null;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzfl(l, l2, n2, arrayList);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 2) {
                if (n4 != 3) {
                    if (n4 != 4) {
                        if (n4 != 5) {
                            SafeParcelReader.skipUnknownField(parcel, n3);
                            continue;
                        }
                        arrayList = SafeParcelReader.createTypedList(parcel, n3, zzh.CREATOR);
                        continue;
                    }
                    n2 = SafeParcelReader.readInt(parcel, n3);
                    continue;
                }
                l2 = SafeParcelReader.readLong(parcel, n3);
                continue;
            }
            l = SafeParcelReader.readLong(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzfl[n];
    }
}

