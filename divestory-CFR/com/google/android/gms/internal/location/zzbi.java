/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.location.zzbh;

public final class zzbi
implements Parcelable.Creator<zzbh> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        double d;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        double d2 = d = 0.0;
        String string2 = null;
        long l = 0L;
        int n2 = 0;
        short s = 0;
        float f = 0.0f;
        int n3 = 0;
        int n4 = -1;
        short s2 = s;
        block11 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzbh(string2, n2, s2, d, d2, f, l, n3, n4);
            }
            s = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(s)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, s);
                    continue block11;
                }
                case 9: {
                    n4 = SafeParcelReader.readInt(parcel, s);
                    continue block11;
                }
                case 8: {
                    n3 = SafeParcelReader.readInt(parcel, s);
                    continue block11;
                }
                case 7: {
                    n2 = SafeParcelReader.readInt(parcel, s);
                    continue block11;
                }
                case 6: {
                    f = SafeParcelReader.readFloat(parcel, s);
                    continue block11;
                }
                case 5: {
                    d2 = SafeParcelReader.readDouble(parcel, s);
                    continue block11;
                }
                case 4: {
                    d = SafeParcelReader.readDouble(parcel, s);
                    continue block11;
                }
                case 3: {
                    s2 = s = (short)SafeParcelReader.readShort(parcel, s);
                    continue block11;
                }
                case 2: {
                    l = SafeParcelReader.readLong(parcel, s);
                    continue block11;
                }
                case 1: 
            }
            string2 = SafeParcelReader.createString(parcel, s);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzbh[n];
    }
}

