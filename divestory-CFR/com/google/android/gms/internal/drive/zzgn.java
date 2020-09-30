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
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.internal.drive.zzgm;

public final class zzgn
implements Parcelable.Creator<zzgm> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String[] arrstring;
        Object object;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string2 = null;
        Object object2 = object = (arrstring = null);
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzgm(string2, arrstring, (DriveId)object, (FilterHolder)object2);
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
                        object2 = SafeParcelReader.createParcelable(parcel, n2, FilterHolder.CREATOR);
                        continue;
                    }
                    object = SafeParcelReader.createParcelable(parcel, n2, DriveId.CREATOR);
                    continue;
                }
                arrstring = SafeParcelReader.createStringArray(parcel, n2);
                continue;
            }
            string2 = SafeParcelReader.createString(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzgm[n];
    }
}

