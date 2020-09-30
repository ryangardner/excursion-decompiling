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
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import java.util.ArrayList;
import java.util.List;

public final class zzf
implements Parcelable.Creator<ActivityTransitionRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList<ActivityTransition> arrayList = null;
        String string2 = null;
        Object object = string2;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new ActivityTransitionRequest((List<ActivityTransition>)arrayList, string2, (List<ClientIdentity>)object);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        SafeParcelReader.skipUnknownField(parcel, n2);
                        continue;
                    }
                    object = SafeParcelReader.createTypedList(parcel, n2, ClientIdentity.CREATOR);
                    continue;
                }
                string2 = SafeParcelReader.createString(parcel, n2);
                continue;
            }
            arrayList = SafeParcelReader.createTypedList(parcel, n2, ActivityTransition.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ActivityTransitionRequest[n];
    }
}

