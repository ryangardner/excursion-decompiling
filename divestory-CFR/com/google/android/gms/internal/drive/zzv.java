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
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzu;

public final class zzv
implements Parcelable.Creator<zzu> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        MetadataBundle metadataBundle = null;
        Object object = metadataBundle;
        AbstractSafeParcelable abstractSafeParcelable = object;
        Object object2 = abstractSafeParcelable;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzu(metadataBundle, n2, (String)object, (DriveId)abstractSafeParcelable, (Integer)object2);
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
                            object2 = SafeParcelReader.readIntegerObject(parcel, n3);
                            continue;
                        }
                        abstractSafeParcelable = SafeParcelReader.createParcelable(parcel, n3, DriveId.CREATOR);
                        continue;
                    }
                    object = SafeParcelReader.createString(parcel, n3);
                    continue;
                }
                n2 = SafeParcelReader.readInt(parcel, n3);
                continue;
            }
            metadataBundle = SafeParcelReader.createParcelable(parcel, n3, MetadataBundle.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzu[n];
    }
}

