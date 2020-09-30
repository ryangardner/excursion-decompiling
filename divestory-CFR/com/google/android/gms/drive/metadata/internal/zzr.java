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
import com.google.android.gms.drive.metadata.internal.zzq;

public final class zzr
implements Parcelable.Creator<zzq> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string2 = null;
        long l = 0L;
        int n2 = -1;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzq(string2, l, n2);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 2) {
                if (n4 != 3) {
                    if (n4 != 4) {
                        SafeParcelReader.skipUnknownField(parcel, n3);
                        continue;
                    }
                    n2 = SafeParcelReader.readInt(parcel, n3);
                    continue;
                }
                l = SafeParcelReader.readLong(parcel, n3);
                continue;
            }
            string2 = SafeParcelReader.createString(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzq[n];
    }
}

