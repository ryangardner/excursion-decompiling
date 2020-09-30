/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;

public final class zzc
implements Parcelable.Creator<Contents> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Object object;
        ParcelFileDescriptor parcelFileDescriptor;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Object object2 = object = (parcelFileDescriptor = null);
        int n2 = 0;
        int n3 = 0;
        boolean bl = false;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new Contents(parcelFileDescriptor, n2, n3, (DriveId)object, bl, (String)object2);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            int n5 = SafeParcelReader.getFieldId(n4);
            if (n5 != 2) {
                if (n5 != 3) {
                    if (n5 != 4) {
                        if (n5 != 5) {
                            if (n5 != 7) {
                                if (n5 != 8) {
                                    SafeParcelReader.skipUnknownField(parcel, n4);
                                    continue;
                                }
                                object2 = SafeParcelReader.createString(parcel, n4);
                                continue;
                            }
                            bl = SafeParcelReader.readBoolean(parcel, n4);
                            continue;
                        }
                        object = SafeParcelReader.createParcelable(parcel, n4, DriveId.CREATOR);
                        continue;
                    }
                    n3 = SafeParcelReader.readInt(parcel, n4);
                    continue;
                }
                n2 = SafeParcelReader.readInt(parcel, n4);
                continue;
            }
            parcelFileDescriptor = (ParcelFileDescriptor)SafeParcelReader.createParcelable(parcel, n4, ParcelFileDescriptor.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Contents[n];
    }
}

