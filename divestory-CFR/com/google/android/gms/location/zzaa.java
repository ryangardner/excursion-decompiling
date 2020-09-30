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
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.zzaj;

public final class zzaa
implements Parcelable.Creator<LocationAvailability> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        long l = 0L;
        zzaj[] arrzzaj = null;
        int n2 = 1000;
        int n3 = 1;
        int n4 = 1;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new LocationAvailability(n2, n3, n4, l, arrzzaj);
            }
            int n5 = SafeParcelReader.readHeader(parcel);
            int n6 = SafeParcelReader.getFieldId(n5);
            if (n6 != 1) {
                if (n6 != 2) {
                    if (n6 != 3) {
                        if (n6 != 4) {
                            if (n6 != 5) {
                                SafeParcelReader.skipUnknownField(parcel, n5);
                                continue;
                            }
                            arrzzaj = SafeParcelReader.createTypedArray(parcel, n5, zzaj.CREATOR);
                            continue;
                        }
                        n2 = SafeParcelReader.readInt(parcel, n5);
                        continue;
                    }
                    l = SafeParcelReader.readLong(parcel, n5);
                    continue;
                }
                n4 = SafeParcelReader.readInt(parcel, n5);
                continue;
            }
            n3 = SafeParcelReader.readInt(parcel, n5);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationAvailability[n];
    }
}

