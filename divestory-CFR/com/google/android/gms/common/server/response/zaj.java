/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.converter.zaa;
import com.google.android.gms.common.server.response.FastJsonResponse;

public final class zaj
implements Parcelable.Creator<FastJsonResponse.Field> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string3 = string2 = null;
        Object object = string3;
        int n2 = 0;
        int n3 = 0;
        boolean bl = false;
        int n4 = 0;
        boolean bl2 = false;
        int n5 = 0;
        block11 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new FastJsonResponse.Field(n2, n3, bl, n4, bl2, string2, n5, string3, (zaa)object);
            }
            int n6 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n6)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n6);
                    continue block11;
                }
                case 9: {
                    object = SafeParcelReader.createParcelable(parcel, n6, zaa.CREATOR);
                    continue block11;
                }
                case 8: {
                    string3 = SafeParcelReader.createString(parcel, n6);
                    continue block11;
                }
                case 7: {
                    n5 = SafeParcelReader.readInt(parcel, n6);
                    continue block11;
                }
                case 6: {
                    string2 = SafeParcelReader.createString(parcel, n6);
                    continue block11;
                }
                case 5: {
                    bl2 = SafeParcelReader.readBoolean(parcel, n6);
                    continue block11;
                }
                case 4: {
                    n4 = SafeParcelReader.readInt(parcel, n6);
                    continue block11;
                }
                case 3: {
                    bl = SafeParcelReader.readBoolean(parcel, n6);
                    continue block11;
                }
                case 2: {
                    n3 = SafeParcelReader.readInt(parcel, n6);
                    continue block11;
                }
                case 1: 
            }
            n2 = SafeParcelReader.readInt(parcel, n6);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new FastJsonResponse.Field[n];
    }
}

