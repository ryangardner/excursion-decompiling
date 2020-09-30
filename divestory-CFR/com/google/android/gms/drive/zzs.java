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
import com.google.android.gms.drive.zzr;

public final class zzs
implements Parcelable.Creator<zzr> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        String string3;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string4 = string3 = (string2 = null);
        int n2 = 0;
        int n3 = 0;
        boolean bl = false;
        block8 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzr(string2, n2, string3, string4, n3, bl);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n4)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n4);
                    continue block8;
                }
                case 7: {
                    bl = SafeParcelReader.readBoolean(parcel, n4);
                    continue block8;
                }
                case 6: {
                    n3 = SafeParcelReader.readInt(parcel, n4);
                    continue block8;
                }
                case 5: {
                    string4 = SafeParcelReader.createString(parcel, n4);
                    continue block8;
                }
                case 4: {
                    string3 = SafeParcelReader.createString(parcel, n4);
                    continue block8;
                }
                case 3: {
                    n2 = SafeParcelReader.readInt(parcel, n4);
                    continue block8;
                }
                case 2: 
            }
            string2 = SafeParcelReader.createString(parcel, n4);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzr[n];
    }
}

