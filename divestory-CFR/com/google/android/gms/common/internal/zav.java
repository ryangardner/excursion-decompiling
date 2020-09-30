/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.zas;

public final class zav
implements Parcelable.Creator<zas> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder iBinder;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Object object = iBinder = null;
        int n2 = 0;
        boolean bl = false;
        boolean bl2 = false;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zas(n2, iBinder, (ConnectionResult)object, bl, bl2);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 4) {
                            if (n4 != 5) {
                                SafeParcelReader.skipUnknownField(parcel, n3);
                                continue;
                            }
                            bl2 = SafeParcelReader.readBoolean(parcel, n3);
                            continue;
                        }
                        bl = SafeParcelReader.readBoolean(parcel, n3);
                        continue;
                    }
                    object = SafeParcelReader.createParcelable(parcel, n3, ConnectionResult.CREATOR);
                    continue;
                }
                iBinder = SafeParcelReader.readIBinder(parcel, n3);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zas[n];
    }
}

