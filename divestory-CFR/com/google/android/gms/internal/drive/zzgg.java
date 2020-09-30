/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.drive.zzgf;

public final class zzgg
implements Parcelable.Creator<zzgf> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder iBinder;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        ParcelFileDescriptor parcelFileDescriptor = null;
        Object object = iBinder = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzgf(parcelFileDescriptor, iBinder, (String)object);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        SafeParcelReader.skipUnknownField(parcel, n2);
                        continue;
                    }
                    object = SafeParcelReader.createString(parcel, n2);
                    continue;
                }
                iBinder = SafeParcelReader.readIBinder(parcel, n2);
                continue;
            }
            parcelFileDescriptor = (ParcelFileDescriptor)SafeParcelReader.createParcelable(parcel, n2, ParcelFileDescriptor.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzgf[n];
    }
}

