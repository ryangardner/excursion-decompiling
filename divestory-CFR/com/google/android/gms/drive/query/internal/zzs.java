/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.query.internal.zzr;
import com.google.android.gms.drive.query.internal.zzx;
import java.util.ArrayList;
import java.util.List;

public final class zzs
implements Parcelable.Creator<zzr> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        zzx zzx2 = null;
        ArrayList<FilterHolder> arrayList = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzr(zzx2, (List<FilterHolder>)arrayList);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            int n3 = SafeParcelReader.getFieldId(n2);
            if (n3 != 1) {
                if (n3 != 2) {
                    SafeParcelReader.skipUnknownField(parcel, n2);
                    continue;
                }
                arrayList = SafeParcelReader.createTypedList(parcel, n2, FilterHolder.CREATOR);
                continue;
            }
            zzx2 = SafeParcelReader.createParcelable(parcel, n2, zzx.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzr[n];
    }
}

