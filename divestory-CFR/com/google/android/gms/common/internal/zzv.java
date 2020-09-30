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
import com.google.android.gms.common.internal.RootTelemetryConfiguration;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzv
implements Parcelable.Creator<RootTelemetryConfiguration> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        int n2 = 0;
        boolean bl = false;
        boolean bl2 = false;
        int n3 = 0;
        int n4 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new RootTelemetryConfiguration(n2, bl, bl2, n3, n4);
            }
            int n5 = SafeParcelReader.readHeader(parcel);
            int n6 = SafeParcelReader.getFieldId(n5);
            if (n6 != 1) {
                if (n6 != 2) {
                    if (n6 != 3) {
                        if (n6 != 4) {
                            if (n6 != 5) {
                                SafeParcelReader.skipUnknownField(parcel, n5);
                                continue;
                            }
                            n4 = SafeParcelReader.readInt(parcel, n5);
                            continue;
                        }
                        n3 = SafeParcelReader.readInt(parcel, n5);
                        continue;
                    }
                    bl2 = SafeParcelReader.readBoolean(parcel, n5);
                    continue;
                }
                bl = SafeParcelReader.readBoolean(parcel, n5);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n5);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new RootTelemetryConfiguration[n];
    }
}

