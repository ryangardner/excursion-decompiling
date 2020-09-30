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
import com.google.android.gms.internal.location.zzbh;
import com.google.android.gms.location.GeofencingRequest;
import java.util.ArrayList;
import java.util.List;

public final class zzq
implements Parcelable.Creator<GeofencingRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList<zzbh> arrayList = null;
        int n2 = 0;
        String string2 = "";
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new GeofencingRequest(arrayList, n2, string2);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        SafeParcelReader.skipUnknownField(parcel, n3);
                        continue;
                    }
                    string2 = SafeParcelReader.createString(parcel, n3);
                    continue;
                }
                n2 = SafeParcelReader.readInt(parcel, n3);
                continue;
            }
            arrayList = SafeParcelReader.createTypedList(parcel, n3, zzbh.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new GeofencingRequest[n];
    }
}

