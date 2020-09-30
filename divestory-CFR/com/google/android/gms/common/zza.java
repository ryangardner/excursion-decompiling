/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zza
implements Parcelable.Creator<ConnectionResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        PendingIntent pendingIntent = null;
        String string2 = null;
        int n2 = 0;
        int n3 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new ConnectionResult(n2, n3, pendingIntent, string2);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            int n5 = SafeParcelReader.getFieldId(n4);
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4) {
                            SafeParcelReader.skipUnknownField(parcel, n4);
                            continue;
                        }
                        string2 = SafeParcelReader.createString(parcel, n4);
                        continue;
                    }
                    pendingIntent = (PendingIntent)SafeParcelReader.createParcelable(parcel, n4, PendingIntent.CREATOR);
                    continue;
                }
                n3 = SafeParcelReader.readInt(parcel, n4);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n4);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ConnectionResult[n];
    }
}

