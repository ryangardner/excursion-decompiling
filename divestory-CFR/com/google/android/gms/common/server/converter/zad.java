/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.converter.StringToIntConverter;

public final class zad
implements Parcelable.Creator<StringToIntConverter.zaa> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        int n2 = 0;
        String string2 = null;
        int n3 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new StringToIntConverter.zaa(n2, string2, n3);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            int n5 = SafeParcelReader.getFieldId(n4);
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        SafeParcelReader.skipUnknownField(parcel, n4);
                        continue;
                    }
                    n3 = SafeParcelReader.readInt(parcel, n4);
                    continue;
                }
                string2 = SafeParcelReader.createString(parcel, n4);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n4);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new StringToIntConverter.zaa[n];
    }
}

