/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.internal.drive.zzgb;
import com.google.android.gms.internal.drive.zzgo;

public final class zzga
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzga> CREATOR = new zzgb();
    private final zzgo zzil;

    zzga(zzgo zzgo2) {
        this.zzil = zzgo2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzil, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final TransferPreferences zzax() {
        return this.zzil;
    }
}

