/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

public final class zzbe
implements Parcelable.Creator<zzbd> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        List<ClientIdentity> list = zzbd.zzcd;
        Object object = null;
        String string3 = string2 = object;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        block8 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzbd((LocationRequest)object, list, string2, bl, bl2, bl3, string3);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 1) {
                switch (n3) {
                    default: {
                        SafeParcelReader.skipUnknownField(parcel, n2);
                        continue block8;
                    }
                    case 10: {
                        string3 = SafeParcelReader.createString(parcel, n2);
                        continue block8;
                    }
                    case 9: {
                        bl3 = SafeParcelReader.readBoolean(parcel, n2);
                        continue block8;
                    }
                    case 8: {
                        bl2 = SafeParcelReader.readBoolean(parcel, n2);
                        continue block8;
                    }
                    case 7: {
                        bl = SafeParcelReader.readBoolean(parcel, n2);
                        continue block8;
                    }
                    case 6: {
                        string2 = SafeParcelReader.createString(parcel, n2);
                        continue block8;
                    }
                    case 5: 
                }
                list = SafeParcelReader.createTypedList(parcel, n2, ClientIdentity.CREATOR);
                continue;
            }
            object = SafeParcelReader.createParcelable(parcel, n2, LocationRequest.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzbd[n];
    }
}

