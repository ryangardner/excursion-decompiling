/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;

public final class zzb
implements Parcelable.Creator<ActivityRecognitionResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l;
        Bundle bundle;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        long l2 = l = 0L;
        Bundle bundle2 = bundle = null;
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new ActivityRecognitionResult((List<DetectedActivity>)bundle, l, l2, n2, bundle2);
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
                            bundle2 = SafeParcelReader.createBundle(parcel, n3);
                            continue;
                        }
                        n2 = SafeParcelReader.readInt(parcel, n3);
                        continue;
                    }
                    l2 = SafeParcelReader.readLong(parcel, n3);
                    continue;
                }
                l = SafeParcelReader.readLong(parcel, n3);
                continue;
            }
            bundle = SafeParcelReader.createTypedList(parcel, n3, DetectedActivity.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ActivityRecognitionResult[n];
    }
}

