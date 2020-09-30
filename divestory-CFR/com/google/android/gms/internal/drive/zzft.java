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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.zzu;
import com.google.android.gms.internal.drive.zzfu;

public final class zzft
extends zzu {
    public static final Parcelable.Creator<zzft> CREATOR = new zzfu();
    final boolean zzea;
    final DataHolder zzii;

    public zzft(DataHolder dataHolder, boolean bl) {
        this.zzii = dataHolder;
        this.zzea = bl;
    }

    @Override
    protected final void zza(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzii, n, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzea);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final DataHolder zzau() {
        return this.zzii;
    }
}

