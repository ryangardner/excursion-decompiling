/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.events.zzo;

public final class zzp
implements Parcelable.Creator<zzo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        boolean bl = false;
        DataHolder dataHolder = null;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzo(dataHolder, bl, n2);
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
                bl = SafeParcelReader.readBoolean(parcel, n3);
                continue;
            }
            dataHolder = SafeParcelReader.createParcelable(parcel, n3, DataHolder.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzo[n];
    }
}

