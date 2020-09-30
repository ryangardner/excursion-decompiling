/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.internal.zza;
import com.google.android.gms.drive.query.internal.zzaa;
import com.google.android.gms.drive.query.internal.zzj;

public final class zzz
extends zza {
    public static final Parcelable.Creator<zzz> CREATOR = new zzaa();

    public final void writeToParcel(Parcel parcel, int n) {
        SafeParcelWriter.finishObjectHeader(parcel, SafeParcelWriter.beginObjectHeader(parcel));
    }

    public final <F> F zza(zzj<F> zzj2) {
        return zzj2.zzbj();
    }
}

