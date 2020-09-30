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
import com.google.android.gms.internal.drive.zzfw;

public final class zzfv
extends zzu {
    public static final Parcelable.Creator<zzfv> CREATOR = new zzfw();
    final DataHolder zzij;

    public zzfv(DataHolder dataHolder) {
        this.zzij = dataHolder;
    }

    @Override
    protected final void zza(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzij, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final DataHolder zzav() {
        return this.zzij;
    }
}

