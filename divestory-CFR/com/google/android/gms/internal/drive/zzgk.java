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
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzgj;

public final class zzgk
implements Parcelable.Creator<zzgj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        int n2 = 0;
        DriveId driveId = null;
        int n3 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzgj(driveId, n2, n3);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            int n5 = SafeParcelReader.getFieldId(n4);
            if (n5 != 2) {
                if (n5 != 3) {
                    if (n5 != 4) {
                        SafeParcelReader.skipUnknownField(parcel, n4);
                        continue;
                    }
                    n3 = SafeParcelReader.readInt(parcel, n4);
                    continue;
                }
                n2 = SafeParcelReader.readInt(parcel, n4);
                continue;
            }
            driveId = SafeParcelReader.createParcelable(parcel, n4, DriveId.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzgj[n];
    }
}

