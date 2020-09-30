/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.location.zzm;
import com.google.android.gms.internal.location.zzo;

public final class zzp
implements Parcelable.Creator<zzo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder iBinder;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        zzm zzm2 = null;
        IBinder iBinder2 = iBinder = null;
        int n2 = 1;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzo(n2, zzm2, iBinder, iBinder2);
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
                        iBinder2 = SafeParcelReader.readIBinder(parcel, n3);
                        continue;
                    }
                    iBinder = SafeParcelReader.readIBinder(parcel, n3);
                    continue;
                }
                zzm2 = SafeParcelReader.createParcelable(parcel, n3, zzm.CREATOR);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzo[n];
    }
}

