/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.UserMetadata;

public final class zzt
implements Parcelable.Creator<UserMetadata> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        String string3;
        String string4;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string5 = string4 = (string3 = (string2 = null));
        boolean bl = false;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new UserMetadata(string2, string3, string4, bl, string5);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        if (n3 != 5) {
                            if (n3 != 6) {
                                SafeParcelReader.skipUnknownField(parcel, n2);
                                continue;
                            }
                            string5 = SafeParcelReader.createString(parcel, n2);
                            continue;
                        }
                        bl = SafeParcelReader.readBoolean(parcel, n2);
                        continue;
                    }
                    string4 = SafeParcelReader.createString(parcel, n2);
                    continue;
                }
                string3 = SafeParcelReader.createString(parcel, n2);
                continue;
            }
            string2 = SafeParcelReader.createString(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new UserMetadata[n];
    }
}

