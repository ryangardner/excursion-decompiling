/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.location.zzal;
import java.util.ArrayList;
import java.util.List;

public final class zzam
implements Parcelable.Creator<zzal> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList<String> arrayList = null;
        String string2 = "";
        PendingIntent pendingIntent = null;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzal(arrayList, pendingIntent, string2);
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
                pendingIntent = (PendingIntent)SafeParcelReader.createParcelable(parcel, n2, PendingIntent.CREATOR);
                continue;
            }
            arrayList = SafeParcelReader.createStringList(parcel, n2);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzal[n];
    }
}
