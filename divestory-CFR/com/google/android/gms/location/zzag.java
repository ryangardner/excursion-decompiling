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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzae;
import java.util.ArrayList;
import java.util.List;

public final class zzag
implements Parcelable.Creator<LocationSettingsRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        boolean bl = false;
        ArrayList<LocationRequest> arrayList = null;
        Object object = arrayList;
        boolean bl2 = false;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new LocationSettingsRequest((List<LocationRequest>)arrayList, bl, bl2, (zzae)object);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 5) {
                            SafeParcelReader.skipUnknownField(parcel, n2);
                            continue;
                        }
                        object = SafeParcelReader.createParcelable(parcel, n2, zzae.CREATOR);
                        continue;
                    }
                    bl2 = SafeParcelReader.readBoolean(parcel, n2);
                    continue;
                }
                bl = SafeParcelReader.readBoolean(parcel, n2);
                continue;
            }
            arrayList = SafeParcelReader.createTypedList(parcel, n2, LocationRequest.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationSettingsRequest[n];
    }
}

