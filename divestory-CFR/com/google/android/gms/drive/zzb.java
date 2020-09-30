/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.zza;

public final class zzb
implements Parcelable.Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l;
        long l2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        long l3 = l = (l2 = 0L);
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zza(l2, l, l3);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        SafeParcelReader.skipUnknownField(parcel, n2);
                        continue;
                    }
                    l3 = SafeParcelReader.readLong(parcel, n2);
                    continue;
                }
                l = SafeParcelReader.readLong(parcel, n2);
                continue;
            }
            l2 = SafeParcelReader.readLong(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zza[n];
    }
}

