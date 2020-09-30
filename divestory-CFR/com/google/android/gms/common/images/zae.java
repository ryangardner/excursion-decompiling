/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zae
implements Parcelable.Creator<WebImage> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        int n2 = 0;
        Uri uri = null;
        int n3 = 0;
        int n4 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new WebImage(n2, uri, n3, n4);
            }
            int n5 = SafeParcelReader.readHeader(parcel);
            int n6 = SafeParcelReader.getFieldId(n5);
            if (n6 != 1) {
                if (n6 != 2) {
                    if (n6 != 3) {
                        if (n6 != 4) {
                            SafeParcelReader.skipUnknownField(parcel, n5);
                            continue;
                        }
                        n4 = SafeParcelReader.readInt(parcel, n5);
                        continue;
                    }
                    n3 = SafeParcelReader.readInt(parcel, n5);
                    continue;
                }
                uri = (Uri)SafeParcelReader.createParcelable(parcel, n5, Uri.CREATOR);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n5);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new WebImage[n];
    }
}

