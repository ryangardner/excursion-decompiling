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
import com.google.android.gms.internal.drive.zzgw;
import java.util.ArrayList;
import java.util.List;

public final class zzgx
implements Parcelable.Creator<zzgw> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        DriveId driveId = null;
        ArrayList<DriveId> arrayList = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzgw(driveId, arrayList);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 2) {
                if (n3 != 3) {
                    SafeParcelReader.skipUnknownField(parcel, n2);
                    continue;
                }
                arrayList = SafeParcelReader.createTypedList(parcel, n2, DriveId.CREATOR);
                continue;
            }
            driveId = SafeParcelReader.createParcelable(parcel, n2, DriveId.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzgw[n];
    }
}

