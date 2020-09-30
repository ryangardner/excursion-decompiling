/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.query.internal.zzf;

public final class zzg
implements Parcelable.Creator<zzf> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string2 = null;
        boolean bl = false;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzf(string2, bl);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 1) {
                if (n3 != 2) {
                    SafeParcelReader.skipUnknownField(parcel, n2);
                    continue;
                }
                bl = SafeParcelReader.readBoolean(parcel, n2);
                continue;
            }
            string2 = SafeParcelReader.createString(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzf[n];
    }
}

