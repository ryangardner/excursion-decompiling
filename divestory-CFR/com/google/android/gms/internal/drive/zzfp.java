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
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.zzb;
import com.google.android.gms.drive.events.zzo;
import com.google.android.gms.drive.events.zzr;
import com.google.android.gms.drive.events.zzv;
import com.google.android.gms.internal.drive.zzfq;

public final class zzfp
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfp> CREATOR = new zzfq();
    private final int zzda;
    private final ChangeEvent zzib;
    private final CompletionEvent zzic;
    private final zzo zzid;
    private final zzb zzie;
    private final zzv zzif;
    private final zzr zzig;

    zzfp(int n, ChangeEvent changeEvent, CompletionEvent completionEvent, zzo zzo2, zzb zzb2, zzv zzv2, zzr zzr2) {
        this.zzda = n;
        this.zzib = changeEvent;
        this.zzic = completionEvent;
        this.zzid = zzo2;
        this.zzie = zzb2;
        this.zzif = zzv2;
        this.zzig = zzr2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzda);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzib, n, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzic, n, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzid, n, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzie, n, false);
        SafeParcelWriter.writeParcelable(parcel, 9, this.zzif, n, false);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzig, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final DriveEvent zzat() {
        int n = this.zzda;
        if (n == 1) return this.zzib;
        if (n == 2) return this.zzic;
        if (n == 3) return this.zzid;
        if (n == 4) return this.zzie;
        if (n == 7) return this.zzif;
        if (n == 8) {
            return this.zzig;
        }
        n = this.zzda;
        StringBuilder stringBuilder = new StringBuilder(33);
        stringBuilder.append("Unexpected event type ");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }
}

