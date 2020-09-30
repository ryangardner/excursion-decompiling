/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.internal.location.zzbf;

public final class zzbg
implements Parcelable.Creator<zzbf> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder iBinder;
        IBinder iBinder2;
        Object object;
        IBinder iBinder3;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        IBinder iBinder4 = iBinder2 = (iBinder = (iBinder3 = (object = null)));
        int n2 = 1;
        block8 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzbf(n2, (zzbd)object, iBinder3, (PendingIntent)iBinder, iBinder2, iBinder4);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n3)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n3);
                    continue block8;
                }
                case 6: {
                    iBinder4 = SafeParcelReader.readIBinder(parcel, n3);
                    continue block8;
                }
                case 5: {
                    iBinder2 = SafeParcelReader.readIBinder(parcel, n3);
                    continue block8;
                }
                case 4: {
                    iBinder = (PendingIntent)SafeParcelReader.createParcelable(parcel, n3, PendingIntent.CREATOR);
                    continue block8;
                }
                case 3: {
                    iBinder3 = SafeParcelReader.readIBinder(parcel, n3);
                    continue block8;
                }
                case 2: {
                    object = SafeParcelReader.createParcelable(parcel, n3, zzbd.CREATOR);
                    continue block8;
                }
                case 1: 
            }
            n2 = SafeParcelReader.readInt(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzbf[n];
    }
}

