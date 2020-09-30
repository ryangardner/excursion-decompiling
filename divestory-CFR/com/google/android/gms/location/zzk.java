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
import com.google.android.gms.location.zzj;

public final class zzk
implements Parcelable.Creator<zzj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        long l = 50L;
        long l2 = Long.MAX_VALUE;
        boolean bl = true;
        float f = 0.0f;
        int n2 = Integer.MAX_VALUE;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzj(bl, l, f, l2, n2);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 4) {
                            if (n4 != 5) {
                                SafeParcelReader.skipUnknownField(parcel, n3);
                                continue;
                            }
                            n2 = SafeParcelReader.readInt(parcel, n3);
                            continue;
                        }
                        l2 = SafeParcelReader.readLong(parcel, n3);
                        continue;
                    }
                    f = SafeParcelReader.readFloat(parcel, n3);
                    continue;
                }
                l = SafeParcelReader.readLong(parcel, n3);
                continue;
            }
            bl = SafeParcelReader.readBoolean(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzj[n];
    }
}

