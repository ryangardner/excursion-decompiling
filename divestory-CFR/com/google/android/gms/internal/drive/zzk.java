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
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.zze;
import com.google.android.gms.drive.events.zzt;
import com.google.android.gms.drive.events.zzx;
import com.google.android.gms.internal.drive.zzj;

public final class zzk
implements Parcelable.Creator<zzj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        AbstractSafeParcelable abstractSafeParcelable;
        AbstractSafeParcelable abstractSafeParcelable2;
        DriveId driveId;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        AbstractSafeParcelable abstractSafeParcelable3 = abstractSafeParcelable2 = (abstractSafeParcelable = (driveId = null));
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzj(driveId, n2, (zze)abstractSafeParcelable, (zzx)abstractSafeParcelable2, (zzt)abstractSafeParcelable3);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 2) {
                if (n4 != 3) {
                    if (n4 != 4) {
                        if (n4 != 5) {
                            if (n4 != 6) {
                                SafeParcelReader.skipUnknownField(parcel, n3);
                                continue;
                            }
                            abstractSafeParcelable3 = SafeParcelReader.createParcelable(parcel, n3, zzt.CREATOR);
                            continue;
                        }
                        abstractSafeParcelable2 = SafeParcelReader.createParcelable(parcel, n3, zzx.CREATOR);
                        continue;
                    }
                    abstractSafeParcelable = SafeParcelReader.createParcelable(parcel, n3, zze.CREATOR);
                    continue;
                }
                n2 = SafeParcelReader.readInt(parcel, n3);
                continue;
            }
            driveId = SafeParcelReader.createParcelable(parcel, n3, DriveId.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzj[n];
    }
}

