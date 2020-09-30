/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.zzc;

public final class zzb
implements Parcelable.Creator<zzc> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Feature[] arrfeature;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Bundle bundle = null;
        Object object = arrfeature = null;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzc(bundle, arrfeature, n2, (ConnectionTelemetryConfiguration)object);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 4) {
                            SafeParcelReader.skipUnknownField(parcel, n3);
                            continue;
                        }
                        object = SafeParcelReader.createParcelable(parcel, n3, ConnectionTelemetryConfiguration.CREATOR);
                        continue;
                    }
                    n2 = SafeParcelReader.readInt(parcel, n3);
                    continue;
                }
                arrfeature = SafeParcelReader.createTypedArray(parcel, n3, Feature.CREATOR);
                continue;
            }
            bundle = SafeParcelReader.createBundle(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzc[n];
    }
}

