/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.drive.zzft;

public final class zzfu
implements Parcelable.Creator<zzft> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        DataHolder dataHolder = null;
        boolean bl = false;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzft(dataHolder, bl);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 2) {
                if (n3 != 3) {
                    SafeParcelReader.skipUnknownField(parcel, n2);
                    continue;
                }
                bl = SafeParcelReader.readBoolean(parcel, n2);
                continue;
            }
            dataHolder = SafeParcelReader.createParcelable(parcel, n2, DataHolder.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzft[n];
    }
}

