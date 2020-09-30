/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzb
implements Parcelable.Creator<Status> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string3 = string2 = null;
        Object object = string3;
        int n2 = 0;
        int n3 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new Status(n2, n3, string2, (PendingIntent)string3, (ConnectionResult)object);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            int n5 = SafeParcelReader.getFieldId(n4);
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4) {
                            if (n5 != 1000) {
                                SafeParcelReader.skipUnknownField(parcel, n4);
                                continue;
                            }
                            n2 = SafeParcelReader.readInt(parcel, n4);
                            continue;
                        }
                        object = SafeParcelReader.createParcelable(parcel, n4, ConnectionResult.CREATOR);
                        continue;
                    }
                    string3 = (PendingIntent)SafeParcelReader.createParcelable(parcel, n4, PendingIntent.CREATOR);
                    continue;
                }
                string2 = SafeParcelReader.createString(parcel, n4);
                continue;
            }
            n3 = SafeParcelReader.readInt(parcel, n4);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Status[n];
    }
}

