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
import com.google.android.gms.location.zzae;

public final class zzaf
implements Parcelable.Creator<zzae> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string3 = "";
        String string4 = string2 = "";
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzae(string3, string2, string4);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 5) {
                        SafeParcelReader.skipUnknownField(parcel, n2);
                        continue;
                    }
                    string3 = SafeParcelReader.createString(parcel, n2);
                    continue;
                }
                string4 = SafeParcelReader.createString(parcel, n2);
                continue;
            }
            string2 = SafeParcelReader.createString(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzae[n];
    }
}

