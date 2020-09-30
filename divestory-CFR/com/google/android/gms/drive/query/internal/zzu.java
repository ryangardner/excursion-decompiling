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
import com.google.android.gms.drive.query.internal.zzt;

public final class zzu
implements Parcelable.Creator<zzt> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzt();
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            SafeParcelReader.getFieldId(n2);
            SafeParcelReader.skipUnknownField(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzt[n];
    }
}

