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
import com.google.android.gms.drive.events.zzt;
import com.google.android.gms.internal.drive.zzgs;

public final class zzgt
implements Parcelable.Creator<zzgs> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        DriveId driveId = null;
        zzt zzt2 = null;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzgs(driveId, n2, zzt2);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 2) {
                if (n4 != 3) {
                    if (n4 != 4) {
                        SafeParcelReader.skipUnknownField(parcel, n3);
                        continue;
                    }
                    zzt2 = SafeParcelReader.createParcelable(parcel, n3, zzt.CREATOR);
                    continue;
                }
                n2 = SafeParcelReader.readInt(parcel, n3);
                continue;
            }
            driveId = SafeParcelReader.createParcelable(parcel, n3, DriveId.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzgs[n];
    }
}

