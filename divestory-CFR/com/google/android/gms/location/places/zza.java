/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.location.places.PlaceReport;

public final class zza
implements Parcelable.Creator<PlaceReport> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string3 = null;
        String string4 = string2 = null;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new PlaceReport(n2, string3, string2, string4);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 4) {
                            SafeParcelReader.skipUnknownField(parcel, n3);
                            continue;
                        }
                        string4 = SafeParcelReader.createString(parcel, n3);
                        continue;
                    }
                    string2 = SafeParcelReader.createString(parcel, n3);
                    continue;
                }
                string3 = SafeParcelReader.createString(parcel, n3);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new PlaceReport[n];
    }
}

