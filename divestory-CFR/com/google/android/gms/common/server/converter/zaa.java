/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.common.server.converter.zab;
import com.google.android.gms.common.server.response.FastJsonResponse;

public final class zaa
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zaa> CREATOR = new zab();
    private final int zaa;
    private final StringToIntConverter zab;

    zaa(int n, StringToIntConverter stringToIntConverter) {
        this.zaa = n;
        this.zab = stringToIntConverter;
    }

    private zaa(StringToIntConverter stringToIntConverter) {
        this.zaa = 1;
        this.zab = stringToIntConverter;
    }

    public static zaa zaa(FastJsonResponse.FieldConverter<?, ?> fieldConverter) {
        if (!(fieldConverter instanceof StringToIntConverter)) throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
        return new zaa((StringToIntConverter)fieldConverter);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zab, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final FastJsonResponse.FieldConverter<?, ?> zaa() {
        StringToIntConverter stringToIntConverter = this.zab;
        if (stringToIntConverter == null) throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
        return stringToIntConverter;
    }
}

