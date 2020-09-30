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
import com.google.android.gms.location.LocationRequest;

public final class zzab
implements Parcelable.Creator<LocationRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        long l = 3600000L;
        long l2 = 600000L;
        long l3 = Long.MAX_VALUE;
        long l4 = 0L;
        int n2 = 102;
        boolean bl = false;
        int n3 = Integer.MAX_VALUE;
        float f = 0.0f;
        block10 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new LocationRequest(n2, l, l2, bl, l3, n3, f, l4);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n4)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n4);
                    continue block10;
                }
                case 8: {
                    l4 = SafeParcelReader.readLong(parcel, n4);
                    continue block10;
                }
                case 7: {
                    f = SafeParcelReader.readFloat(parcel, n4);
                    continue block10;
                }
                case 6: {
                    n3 = SafeParcelReader.readInt(parcel, n4);
                    continue block10;
                }
                case 5: {
                    l3 = SafeParcelReader.readLong(parcel, n4);
                    continue block10;
                }
                case 4: {
                    bl = SafeParcelReader.readBoolean(parcel, n4);
                    continue block10;
                }
                case 3: {
                    l2 = SafeParcelReader.readLong(parcel, n4);
                    continue block10;
                }
                case 2: {
                    l = SafeParcelReader.readLong(parcel, n4);
                    continue block10;
                }
                case 1: 
            }
            n2 = SafeParcelReader.readInt(parcel, n4);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationRequest[n];
    }
}

