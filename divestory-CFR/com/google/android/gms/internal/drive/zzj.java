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
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.zze;
import com.google.android.gms.drive.events.zzt;
import com.google.android.gms.drive.events.zzx;
import com.google.android.gms.internal.drive.zzk;

public final class zzj
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();
    private final zze zzbv;
    final int zzda;
    private final zzx zzdb;
    private final zzt zzdc;
    final DriveId zzk;

    public zzj(int n, DriveId driveId) {
        this(Preconditions.checkNotNull(driveId), 1, null, null, null);
    }

    zzj(DriveId driveId, int n, zze zze2, zzx zzx2, zzt zzt2) {
        this.zzk = driveId;
        this.zzda = n;
        this.zzbv = zze2;
        this.zzdb = zzx2;
        this.zzdc = zzt2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzk, n, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzda);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzbv, n, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzdb, n, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzdc, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

