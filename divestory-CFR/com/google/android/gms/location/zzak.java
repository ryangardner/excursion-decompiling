/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.location.zzaj;

public final class zzak
implements Parcelable.Creator<zzaj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        long l2 = l = -1L;
        int n2 = 1;
        int n3 = 1;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzaj(n2, n3, l, l2);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            int n5 = SafeParcelReader.getFieldId(n4);
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4) {
                            SafeParcelReader.skipUnknownField(parcel, n4);
                            continue;
                        }
                        l2 = SafeParcelReader.readLong(parcel, n4);
                        continue;
                    }
                    l = SafeParcelReader.readLong(parcel, n4);
                    continue;
                }
                n3 = SafeParcelReader.readInt(parcel, n4);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n4);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzaj[n];
    }
}

