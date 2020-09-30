/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.common.internal.RootTelemetryConfiguration;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzd
implements Parcelable.Creator<ConnectionTelemetryConfiguration> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int[] arrn;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        int[] arrn2 = arrn = null;
        boolean bl = false;
        boolean bl2 = false;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new ConnectionTelemetryConfiguration((RootTelemetryConfiguration)arrn, bl, bl2, arrn2, n2);
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
                            n2 = SafeParcelReader.readInt(parcel, n3);
                            continue;
                        }
                        arrn2 = SafeParcelReader.createIntArray(parcel, n3);
                        continue;
                    }
                    bl2 = SafeParcelReader.readBoolean(parcel, n3);
                    continue;
                }
                bl = SafeParcelReader.readBoolean(parcel, n3);
                continue;
            }
            arrn = SafeParcelReader.createParcelable(parcel, n3, RootTelemetryConfiguration.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ConnectionTelemetryConfiguration[n];
    }
}

