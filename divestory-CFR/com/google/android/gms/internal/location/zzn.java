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
import com.google.android.gms.internal.location.zzm;
import com.google.android.gms.location.zzj;
import java.util.List;

public final class zzn
implements Parcelable.Creator<zzm> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        zzj zzj2 = zzm.zzce;
        List<ClientIdentity> list = zzm.zzcd;
        String string2 = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzm(zzj2, list, string2);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        SafeParcelReader.skipUnknownField(parcel, n2);
                        continue;
                    }
                    string2 = SafeParcelReader.createString(parcel, n2);
                    continue;
                }
                list = SafeParcelReader.createTypedList(parcel, n2, ClientIdentity.CREATOR);
                continue;
            }
            zzj2 = SafeParcelReader.createParcelable(parcel, n2, zzj.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzm[n];
    }
}

