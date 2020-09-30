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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.zza;
import com.google.android.gms.internal.drive.zzff;
import java.util.ArrayList;
import java.util.List;

public final class zzfg
implements Parcelable.Creator<zzff> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        DataHolder dataHolder = null;
        ArrayList<DriveId> arrayList = null;
        Object object = arrayList;
        boolean bl = false;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzff(dataHolder, (List<DriveId>)arrayList, (zza)object, bl);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        if (n3 != 5) {
                            SafeParcelReader.skipUnknownField(parcel, n2);
                            continue;
                        }
                        bl = SafeParcelReader.readBoolean(parcel, n2);
                        continue;
                    }
                    object = SafeParcelReader.createParcelable(parcel, n2, zza.CREATOR);
                    continue;
                }
                arrayList = SafeParcelReader.createTypedList(parcel, n2, DriveId.CREATOR);
                continue;
            }
            dataHolder = SafeParcelReader.createParcelable(parcel, n2, DataHolder.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzff[n];
    }
}

