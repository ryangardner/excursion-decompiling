/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.location.zzae;

public final class zzad
extends AbstractSafeParcelable
implements Result {
    public static final Parcelable.Creator<zzad> CREATOR;
    private static final zzad zzcr;
    private final Status zzbl;

    static {
        zzcr = new zzad(Status.RESULT_SUCCESS);
        CREATOR = new zzae();
    }

    public zzad(Status status) {
        this.zzbl = status;
    }

    @Override
    public final Status getStatus() {
        return this.zzbl;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.getStatus(), n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

