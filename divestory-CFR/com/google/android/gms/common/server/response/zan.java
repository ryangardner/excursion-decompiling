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
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zai;

public final class zan
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zan> CREATOR = new zai();
    final String zaa;
    final FastJsonResponse.Field<?, ?> zab;
    private final int zac;

    zan(int n, String string2, FastJsonResponse.Field<?, ?> field) {
        this.zac = n;
        this.zaa = string2;
        this.zab = field;
    }

    zan(String string2, FastJsonResponse.Field<?, ?> field) {
        this.zac = 1;
        this.zaa = string2;
        this.zab = field;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zac);
        SafeParcelWriter.writeString(parcel, 2, this.zaa, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zab, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

