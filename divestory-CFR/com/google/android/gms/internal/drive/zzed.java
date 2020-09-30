/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.drive.zzec;

public final class zzed
implements Parcelable.Creator<zzec> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        IBinder iBinder = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzec(iBinder);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            if (SafeParcelReader.getFieldId(n2) != 2) {
                SafeParcelReader.skipUnknownField(parcel, n2);
                continue;
            }
            iBinder = SafeParcelReader.readIBinder(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzec[n];
    }
}

