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
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.events.zze;
import java.util.ArrayList;
import java.util.List;

public final class zzf
implements Parcelable.Creator<zze> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        int n2 = 0;
        ArrayList<DriveSpace> arrayList = null;
        boolean bl = false;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zze(n2, bl, arrayList);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 2) {
                if (n4 != 3) {
                    if (n4 != 4) {
                        SafeParcelReader.skipUnknownField(parcel, n3);
                        continue;
                    }
                    arrayList = SafeParcelReader.createTypedList(parcel, n3, DriveSpace.CREATOR);
                    continue;
                }
                bl = SafeParcelReader.readBoolean(parcel, n3);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zze[n];
    }
}

