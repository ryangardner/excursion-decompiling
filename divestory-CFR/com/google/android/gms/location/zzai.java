/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.location.LocationSettingsStates;

public final class zzai
implements Parcelable.Creator<LocationSettingsStates> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        block8 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new LocationSettingsStates(bl, bl2, bl3, bl4, bl5, bl6);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n2)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n2);
                    continue block8;
                }
                case 6: {
                    bl6 = SafeParcelReader.readBoolean(parcel, n2);
                    continue block8;
                }
                case 5: {
                    bl5 = SafeParcelReader.readBoolean(parcel, n2);
                    continue block8;
                }
                case 4: {
                    bl4 = SafeParcelReader.readBoolean(parcel, n2);
                    continue block8;
                }
                case 3: {
                    bl3 = SafeParcelReader.readBoolean(parcel, n2);
                    continue block8;
                }
                case 2: {
                    bl2 = SafeParcelReader.readBoolean(parcel, n2);
                    continue block8;
                }
                case 1: 
            }
            bl = SafeParcelReader.readBoolean(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationSettingsStates[n];
    }
}

